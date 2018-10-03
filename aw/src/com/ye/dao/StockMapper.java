package com.ye.dao;

import java.util.List;

import com.ye.pojo.Stock;

public interface StockMapper {
	public List<Stock> getAll();

	public void update(Stock sts);

	public void save(Stock sts);

	public Stock getStock(String code);

	public List<Stock> getNoPinYinStock();
	
	public List<Stock> getStockByParam(String param);
}
