/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bj.park.modules.park.web;

import com.bj.park.common.config.Global;
import com.bj.park.common.persistence.Page;
import com.bj.park.common.utils.StringUtils;
import com.bj.park.common.utils.TimeUtils;
import com.bj.park.common.web.BaseController;
import com.bj.park.modules.park.entity.ParkOrder;
import com.bj.park.modules.park.service.ParkOrderService;
import com.bj.park.modules.sys.entity.User;
import com.bj.park.modules.sys.service.SystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 停车订单管理Controller
 * @author jiahj
 * @version 2018-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/park/parkOrder")
public class ParkOrderController extends BaseController {

	@Autowired
	private ParkOrderService parkOrderService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public ParkOrder get(@RequestParam(required=false) String id) {
		ParkOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = parkOrderService.get(id);
		}
		if (entity == null){
			entity = new ParkOrder();
		}
		return entity;
	}

	@RequiresPermissions("park:parkOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(ParkOrder parkOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ParkOrder> page = parkOrderService.findPage(new Page<ParkOrder>(request, response), parkOrder);
		model.addAttribute("page", page);
		return "modules/park/parkOrderList";
	}

	@ResponseBody
	@RequestMapping(value = {"getOrderHistoryListById"})
	public List<ParkOrder> getOrderHistoryListById(int pageNo,int pageSize, ParkOrder parkOrder) {
		List<ParkOrder> list = parkOrderService.findHistoryList(pageNo, pageSize, parkOrder);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = {"getOrderListById"})
	public List<ParkOrder> getOrderListById(ParkOrder parkOrder) {
		List<ParkOrder> list = parkOrderService.findList(parkOrder);
		return list;
	}

	@RequiresPermissions("park:parkOrder:view")
	@RequestMapping(value = "form")
	public String form(ParkOrder parkOrder, Model model) {
		model.addAttribute("parkOrder", parkOrder);
		return "modules/park/parkOrderForm";
	}

	//@RequiresPermissions("park:parkOrder:edit")    //匿名访问时，该权限会导致无法访问
	@RequestMapping(value = "save")
	public String save(ParkOrder parkOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, parkOrder)){
			return form(parkOrder, model);
		}

		//新增时，更新开始时间及用户ID
		if (null == parkOrder.getId() || parkOrder.getId().isEmpty()) {
			// 开始停车时间
			parkOrder.setStartTime(new Date());

			// 设置用户ID
			User user = systemService.getUserByLoginName(parkOrder.getLoginName());
			parkOrder.setUser(user);
		} else if (null != parkOrder.getId() && "1".equals(parkOrder.getStopFlag())) {
			// 出库时更新出库时间
			parkOrder.setEndTime(new Date());
			Date strTime1 = parkOrder.getEndTime();
			Date strTime2 = parkOrder.getStartTime();
			long time = strTime1.getTime() - strTime2.getTime();
			parkOrder.setParkTime(TimeUtils.toTimeString(time));
			//停车时长
			Long pay = Long.valueOf(0);
			if (null != parkOrder.getFreeTime()) {
				// 免费时段时，停车金额为0
				if (time <= parkOrder.getFreeTime() * 60 * 1000) {
					parkOrder.setParkPay(0);
				} else {
					//停车费用计算
					long h = time / (60*60*1000) + time % (60*60*1000) > 0 ? 1 : 0;
					long parkTime = h / parkOrder.getPriceTime() + h % parkOrder.getPriceTime() > 0 ? 1 : 0;
					pay = parkTime * parkOrder.getPrice();
				}
			} else {
				//停车费用计算
				long h = time / (60*60*1000) + time % (60*60*1000) > 0 ? 1 : 0;
				long parkTime = h / parkOrder.getPriceTime() + h % parkOrder.getPriceTime() > 0 ? 1 : 0;
				pay = parkTime * parkOrder.getPrice();
			}

			parkOrder.setParkPay(pay.intValue());
		}

		parkOrderService.save(parkOrder);
		addMessage(redirectAttributes, "保存停车订单成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkOrder/?repage";
	}

	@RequiresPermissions("park:parkOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ParkOrder parkOrder, RedirectAttributes redirectAttributes) {
		parkOrderService.delete(parkOrder);
		addMessage(redirectAttributes, "删除停车订单成功");
		return "redirect:"+Global.getAdminPath()+"/park/parkOrder/?repage";
	}

}
