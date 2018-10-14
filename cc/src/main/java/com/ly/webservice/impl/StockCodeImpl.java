package com.ly.webservice.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.ly.dao.StockDao;
import com.ly.pojo.Stock;
import com.ly.webservice.StockCode;


//本文https://www.cnblogs.com/moon521/p/5564504.html?tdsourcetag=s_pcqq_aiomsg
//https://www.cnblogs.com/foxting/p/6940258.html
//https://www.cnblogs.com/moon521/p/5564504.html?tdsourcetag=s_pcqq_aiomsg
//https://blog.csdn.net/tzdwsy/article/details/51938786
//https://blog.csdn.net/csolo/article/details/54839227

@WebService(endpointInterface="com.ly.webservice.StockCode")
public class StockCodeImpl implements StockCode{
	
	
	@Autowired
	private StockDao stockDao;
	

    public String getStock(String stockCode) {
    	HashMap map = new HashMap();
    	map.put("code", stockCode);
    	List list  = stockDao.selectStockByParam(map);
    	if(list!=null&&list.size()>0){
    		return list.get(0).toString();
    	}else{
    		return "\"code\":无此数据";
    	}
    	
    }

}