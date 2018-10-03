package com.ye.pojo;

public class Stock {

	private String stockCode;
	private String name;
	private String price;
	private String minPrice;
	private String maxPrice;
	private String preClosePrice;
	private String pointerPrice;
	private String rase;
	private String pinyin;

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getRase() {
		return rase;
	}

	public void setRase(String rase) {
		this.rase = rase;
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

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getPreClosePrice() {
		return preClosePrice;
	}

	public void setPreClosePrice(String preClosePrice) {
		this.preClosePrice = preClosePrice;
	}

	public String getPointerPrice() {
		return pointerPrice;
	}

	public void setPointerPrice(String pointerPrice) {
		this.pointerPrice = pointerPrice;
	}

	@Override
	public String toString() {
		return "Stock [stockCode=" + stockCode + ", name=" + name + ", price=" + price + ", minPrice=" + minPrice
				+ ", maxPrice=" + maxPrice + ", preClosePrice=" + preClosePrice + ", pointerPrice=" + pointerPrice
				+ ", rase=" + rase + ", pinyin=" + pinyin + "]";
	}


}
