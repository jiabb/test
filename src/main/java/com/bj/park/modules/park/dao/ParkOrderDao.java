/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bj.park.modules.park.dao;

import com.bj.park.common.persistence.CrudDao;
import com.bj.park.common.persistence.annotation.MyBatisDao;
import com.bj.park.modules.park.entity.ParkOrder;

import java.util.List;

/**
 * 停车订单管理DAO接口
 * @author jiahj
 * @version 2018-09-26
 */
@MyBatisDao
public interface ParkOrderDao extends CrudDao<ParkOrder> {
	public List<ParkOrder> findHistoryList(ParkOrder parkOrder);
}
