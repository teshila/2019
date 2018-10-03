package com.ye.dao;

import java.util.List;

import com.ye.pojo.StockTradeDeal;

public interface StockTradeDealMapper {
	public List getAll();

	public void save(StockTradeDeal stockTradeDeal);
}
