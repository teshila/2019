package com.ye.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StockDayInfo {

	private String stockCode;
	private String name;
	private String price;
	private Date addDate;
	private String maxPrice;
	private String preClosePrice;
	private String minPrice;
	private String rase;
	

	public String getRase() {
		return rase;
	}

	public void setRase(String rase) {
		this.rase = rase;
	}

	public String getPreClosePrice() {
		return preClosePrice;
	}

	public void setPreClosePrice(String preClosePrice) {
		this.preClosePrice = preClosePrice;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	//https://www.cnblogs.com/gyjx2016/p/6207328.html
	//@JsonFormat(timezone = "GMT+8", pattern = "yyyy-mm-dd")
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

}
