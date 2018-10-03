package com.ye.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ye.common.utils.IPUtils;
import com.ye.common.utils.UUIDTools;
import com.ye.common.utils.ip.TmIpUtil;
import com.ye.common.utils.pinyin.Pinyin;
import com.ye.dao.IPRecordeMapper;
import com.ye.dao.StockDayInfoMapper;
import com.ye.dao.StockMapper;
import com.ye.http.web.PingAnWeb;
import com.ye.pojo.IPAddressRecord;
import com.ye.pojo.Stock;
import com.ye.pojo.StockDayInfo;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

@Controller
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockMapper stockDao;

	@Autowired
	private IPRecordeMapper ipDao;
	
	@Autowired
	private PingAnWeb pingAnWeb;
	
	@Autowired
	private StockDayInfoMapper stockDayInfoMapper;
	
	
	//https://www.cnblogs.com/kangoroo/p/7998433.html
	//https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
	//Page page = PageHelper.startPage(pageNum, pageSize, true); - true表示需要统计总数，这样会多进行一次请求select count(0); 省略掉true参数只返回分页数据
	
	/*本文
	 * http://www.jb51.net/article/136968.htm
	 */

	//https://www.jianshu.com/p/2980a4e84ca2
	//https://www.cnblogs.com/ljdblog/p/6725094.html
	//https://www.jianshu.com/p/2980a4e84ca2
	//https://gitee.com/zhaowg3/ShopManage/tree/master/shopmanage-project/shpm-emall-project/src/main/generate/com/ai/emall
	//https://blog.csdn.net/FansUnion/article/details/40304187 得到分页总数
	//https://www.oschina.net/news/53808/mybatis_pagehelper-3-2-2?p=2#comments
	/*@RequestMapping("/list")
	@ResponseBody
	public Map getAllStock(HttpServletRequest request,String random,String pageIndex,String pageSize) {
		if(random==null||random.equals("undefined")){
			String ipAdd = IPUtils.getIpAddr(request);
			IPAddressRecord ip = new IPAddressRecord();
			ip.setIpadd(ipAdd);
			String area = TmIpUtil.ipLocation(request);
			String id = UUIDTools.getUUID();
			ip.setId(id);
			ip.setIpregion(area);
			
			//获取浏览器信息
			Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
			//获取浏览器版本号
			Version version = browser.getVersion(request.getHeader("User-Agent"));
			String info = browser.getName() + "/" + version.getVersion();
			String osName = System.getProperty("os.name" );
			String osVersion=System.getProperty("os.version");
			
			ip.setOsname(osName);
			ip.setOsversion(osVersion);
			ip.setBrowserVersion(info);
			
			ipDao.save(ip);
		}
		
		//https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
		//https://blog.csdn.net/u012562943/article/details/51838759
		//Page page = PageHelper.startPage(5, 20,true);
		
		int index = Integer.valueOf(pageIndex);
		int size = Integer.valueOf(pageSize);
		
		Page page = PageHelper.startPage(index, size,true);
		List<Stock> list = stockDao.getAll();
		Map map = new HashMap();
		map.put("total", page.getTotal());
		map.put("list",page.getResult());
		//System.out.println(page.getResult()); //和下面注释是两种一样的
		//System.out.println(list);
		return map;
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/list")
	@ResponseBody
	public Map getAllStock(HttpServletRequest request) {
		String ipAdd = IPUtils.getIpAddr(request);
		IPAddressRecord ip = new IPAddressRecord();
		ip.setIpadd(ipAdd);
		String area = TmIpUtil.ipLocation(request);
		String id = UUIDTools.getUUID();
		ip.setId(id);
		ip.setIpregion(area);

		// 获取浏览器信息
		Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
		// 获取浏览器版本号
		Version version = browser.getVersion(request.getHeader("User-Agent"));
		String info = browser.getName() + "/" + version.getVersion();
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");

		ip.setOsname(osName);
		ip.setOsversion(osVersion);
		ip.setBrowserVersion(info);
		List listIp = ipDao.getIpListByIP(ipAdd);
		if(listIp==null||listIp.size()==0){
			ipDao.save(ip);
		}

		List<Stock> list = stockDao.getAll();
		Map map = new HashMap();
		map.put("total",0);
		map.put("list",list);
		return map;
	}
	
	
	
	//https://blog.csdn.net/csdn___lyy/article/details/77160488
	@RequestMapping("/quShi")
	@ResponseBody
	public Map getAllquShiStock(HttpServletRequest request,String page,String rows) {
		/*int index = Integer.valueOf(page);
		int size = Integer.valueOf(rows);
		Page pageHelper = PageHelper.startPage(index, size,true);
		List<Stock> listStock = stockDao.getAll();
		Map map = null;
		Map returnMap = new HashMap();
		returnMap.put("total", pageHelper.getTotal());
		List results = null;
		List returnResult = new ArrayList();
		for (Stock stock : listStock) {
			List<StockDayInfo> infos = stockDayInfoMapper.getStockDayInfo(stock.getStockCode());
			map = new HashMap();
			map.put("code", stock.getStockCode());
			results = new ArrayList();
			for (StockDayInfo stockDayInfo : infos) {
				results.add(stockDayInfo.getPrice());
			}
			map.put("result", results);
			returnResult.add(map);
		}
		
		returnMap.put("list", returnResult);
		return  returnMap;*/
		
		int index = Integer.valueOf(page);
		int size = Integer.valueOf(rows);
		Page pageHelper = PageHelper.startPage(index, size,true);
		Map returnMap = new HashMap();
		List<Stock> listStock = stockDao.getAll();
		returnMap.put("total", pageHelper.getTotal());
		List returnList = new ArrayList();
		Map dayInfoMap = null;
		Map dayPriceMap = null;
		List dayPriceList= null;
		for (Stock stock : listStock) {
			dayInfoMap  = new HashMap();
			dayPriceList = new ArrayList();
			dayInfoMap.put("code", stock.getStockCode());
			dayInfoMap.put("name", stock.getName());
			dayInfoMap.put("todayPrice", stock.getPrice());
			dayInfoMap.put("prevPrice", stock.getPreClosePrice());
			List<StockDayInfo> infos = stockDayInfoMapper.getStockDayInfo(stock.getStockCode());
			if(infos!=null&&infos.size()>0){
				dayPriceMap = new HashMap();
				for (int i = 2; i < infos.size(); i++) {
					dayPriceMap.put("date",getStringDate(infos.get(i).getAddDate()));
					System.out.println("=====>" +getStringDate(infos.get(i).getAddDate()));
					dayPriceMap.put("price",infos.get(i).getPrice());
					dayPriceList.add(dayPriceMap);
				}
				dayInfoMap.put("datePrice", dayPriceList);
			}
			returnList.add(dayInfoMap);
		}
		
		returnMap.put("list", returnList);

		return returnMap;
	}
	
	
	@RequestMapping("/getStockInfo")
	@ResponseBody
	public Stock getStockInfoFromWeb(String code) throws JsonParseException {
		Stock sto = new Stock();
		sto.setStockCode(code);
		try {
			Map map = pingAnWeb.getStockInfo(sto);
			String name = (String) map.get("stockName");
			String prevClose = (String) map.get("prevClose");
			String newPrice = (String) map.get("newPrice");
			String maxPrice  = (String) map.get("maxPrice");
			String minPrice  = (String) map.get("minPrice");
			String pinyin  = Pinyin.getPinYinHeadChar(name);
			sto.setName(name);
			sto.setMaxPrice(maxPrice);
			sto.setMinPrice(minPrice);
			sto.setPrice(newPrice);
			sto.setPreClosePrice(prevClose);
			String pointerPrice = sto.getPointerPrice();
			sto.setPointerPrice(pointerPrice);
			sto.setPinyin(pinyin.toUpperCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sto;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public String save(Stock stock) {
		try {
			if(stock.getStockCode()!=null){
				Stock s = stockDao.getStock(stock.getStockCode());
				if(s==null){
					stockDao.save(stock);
					return "1";
				}else{
					return "3";
				}
			}else{
				return "4";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		
	}
	
	
	
/*	@RequestMapping("queryStock")
	@ResponseBody
	public Map queryStock(String keyword,String pageIndex,String pageSize){
		Map map = new HashMap();
		int index = Integer.valueOf(pageIndex);
		int size = Integer.valueOf(pageSize);
		Page page = PageHelper.startPage(index, size,true);
		System.out.println(page);
		List list = stockDao.getStockByParam(keyword);
		map.put("total", page.getTotal());
		map.put("list",list);
		return map;
	}*/
	
	
	@RequestMapping("queryStock")
	@ResponseBody
	public Map queryStock(String keyword){
		Map map = new HashMap();
		List list = stockDao.getStockByParam(keyword);
		map.put("list",list);
		return map;
	}
	
	
	
	public  String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}
}
