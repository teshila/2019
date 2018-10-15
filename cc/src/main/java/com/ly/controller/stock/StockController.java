package com.ly.controller.stock;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.ly.dao.StockDao;
import com.ly.pojo.Stock;

@Controller
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockDao stockDao;
	
	
	//单个
	@RequestMapping("/getStock")
	@ResponseBody
	public List getStock(String code){
		HashMap map = new HashMap();
		map.put("code",code);
		List list = stockDao.selectStockByParam(map);
		return list;
	}
	
	
	
	@RequestMapping("/moImport")
	@ResponseBody
	public List moImport(String code){
		HashMap map = new HashMap();
		map.put("isMon", 1);
		List list = stockDao.selectStockByParam(map);
		return list;
	}
	
	
	@RequestMapping("/updateInfo")
	@ResponseBody
	public int updateStock(String code,String text){
		HashMap map = new HashMap();
		map.put("code", code);
		map.put("code",code);
		List list = stockDao.selectStockByParam(map);
		Integer flag = Integer.valueOf(text);
		int i = 0;
		if(list!=null&&list.size()>0){
			Stock s = (Stock) list.get(0);
			s.setIsMon(flag);
			stockDao.save(s);
			return 1;
		}
		return 0;
	}
	
	
}
