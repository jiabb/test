/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bj.park.modules.park.service;

import com.bj.park.common.persistence.Page;
import com.bj.park.common.service.CrudService;
import com.bj.park.modules.park.dao.ParkOrderDao;
import com.bj.park.modules.park.entity.ParkOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 停车订单管理Service
 * @author jiahj
 * @version 2018-09-26
 */
@Service
@Transactional(readOnly = true)
public class ParkOrderService extends CrudService<ParkOrderDao, ParkOrder> {
	@Autowired
	private ParkOrderDao parkOrderDao;

	public ParkOrder get(String id) {
		return super.get(id);
	}
	
	public List<ParkOrder> findList(ParkOrder parkOrder) {
		return super.findList(parkOrder);
	}

	public List<ParkOrder> findHistoryList(int pageNo,int pageSize, ParkOrder parkOrder) {
		Page  page = new Page(pageNo, pageSize);
		parkOrder.setPage(page);
		List<ParkOrder> list = parkOrderDao.findHistoryList(parkOrder);
		return list;
	}

	public Page<ParkOrder> findPage(Page<ParkOrder> page, ParkOrder parkOrder) {
		return super.findPage(page, parkOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(ParkOrder parkOrder) {
		super.save(parkOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(ParkOrder parkOrder) {
		super.delete(parkOrder);
	}
	
}
