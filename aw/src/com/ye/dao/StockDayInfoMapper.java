package com.ye.dao;

import java.util.List;

import com.ye.pojo.StockDayInfo;

public interface StockDayInfoMapper{
	
	
	public List getAll();

	public void save(StockDayInfo stockDayInfo);
	
	public List<StockDayInfo> getStockDayInfo(String code);

}
