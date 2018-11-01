/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bj.park.modules.park.entity;

import com.bj.park.common.persistence.DataEntity;
import com.bj.park.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 停车订单管理Entity
 * @author jiahj
 * @version 2018-09-26
 */
public class ParkOrder extends DataEntity<ParkOrder> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 归属用户
	private String loginName;		// 登录用户名
	private String imgUrl;		// 图片URL
	private Date startTime;		// 开始停车时间
	private Date endTime;		// 结束停车时间
	private String parkTime;		// 停车时长
	private String address;		// 停车地点
	private Integer prepay;		// 预付款
	private Integer freeTime;		// 免费时长
	private Integer price;		// 单价
	private Integer priceTime;		// 免计价时间单位
	private Integer parkPay;		//停车金额
	private String stopFlag; 	// 出库标记（0：停车；1：出库）
	
	public ParkOrder() {
		super();
	}

	public ParkOrder(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=250, message="图片URL长度必须介于 0 和 250 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=0, max=100, message="停车时长长度必须介于 0 和 100 之间")
	public String getParkTime() {
		return parkTime;
	}

	public void setParkTime(String parkTime) {
		this.parkTime = parkTime;
	}
	
	@Length(min=0, max=1000, message="停车地点长度必须介于 0 和 1000 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getPrepay() {
		return prepay;
	}

	public void setPrepay(Integer prepay) {
		this.prepay = prepay;
	}
	
	public Integer getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
	}

	public String getStopFlag() {
		return stopFlag;
	}

	public ParkOrder setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
		return this;
	}

	public String getLoginName() {
		return loginName;
	}

	public ParkOrder setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}

	public Integer getPrice() {
		return price;
	}

	public ParkOrder setPrice(Integer price) {
		this.price = price;
		return this;
	}

	public Integer getPriceTime() {
		return priceTime;
	}

	public ParkOrder setPriceTime(Integer priceTime) {
		this.priceTime = priceTime;
		return this;
	}

	public Integer getParkPay() {
		return parkPay;
	}

	public ParkOrder setParkPay(Integer parkPay) {
		this.parkPay = parkPay;
		return this;
	}
}
