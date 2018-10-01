package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.StockDayDealsRecord;

public interface StockDealDao {

	public List selectAllByPage(Map map);

	public List selectAll();

	public void save(StockDayDealsRecord stockRecord);

}
