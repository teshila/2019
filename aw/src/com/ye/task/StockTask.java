package com.ye.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ye.common.utils.UUIDTools;
import com.ye.common.utils.pinyin.PinyinUtilsPro;
import com.ye.dao.EmailAddressMapper;
import com.ye.dao.FileInfoMapper;
import com.ye.dao.GetWebInfoMapper;
import com.ye.dao.HolidayMapper;
import com.ye.dao.StockDayInfoMapper;
import com.ye.dao.StockMapper;
import com.ye.dao.StockTradeDealMapper;
import com.ye.http.HttpClientService;
import com.ye.http.web.PingAnWeb;
import com.ye.mail.MailModel;
import com.ye.mail.impl.EmailServiceImpl;
import com.ye.pojo.FileInfo;
import com.ye.pojo.Holiday;
import com.ye.pojo.Stock;
import com.ye.pojo.StockDayInfo;
import com.ye.pojo.StockTradeDeal;
import com.ye.pojo.WebInfo;


//https://github.com/mybatis-book/book
@Component
public class StockTask {
	@Autowired
	private StockMapper stockDao;

	@Autowired
	private StockDayInfoMapper stockDayInfoDao;

	@Autowired
	private StockTradeDealMapper stockTradeDealDao;
	
	@Autowired
	private EmailServiceImpl  emailServiceImpl;
	
	@Autowired
	private EmailAddressMapper  emailAddressDao;
	
	@Autowired
	private PingAnWeb pingAnWeb;
	
	@Autowired
	private HolidayMapper holidayDao;
	
	
	@Autowired
	private GetWebInfoMapper getWebInfoMapper;
	
	
	
	@Autowired
	private FileInfoMapper fileInfoMapper;
	
	@Autowired
	private HttpClientService httpClientService;
	private static final Logger stdout = Logger.getLogger("stdout");
	private static final Logger file = Logger.getLogger("file");
	private static final Logger register = Logger.getLogger("register");
	private static final Logger login = Logger.getLogger("login");
	private static final Logger jsoup = Logger.getLogger("jsoup");

	public boolean getIsBegin() {
		boolean flag = false;

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY); // http://blog.csdn.net/jiangeeq/article/details/53103069
		int minute = c.get(Calendar.MINUTE);
		flag = hour == 11 && minute >= 30 || hour == 9 && minute <=29;
		return flag;
	}
	

	
	public boolean getIsHoliday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		//System.out.println("当前日期=============>" + df.format(new Date()));
		Holiday h = holidayDao.getIsHoliday();
		if (h != null) {
			//log.log(Level.OFF, "当前节日" + h.getHolidayName() + ",系统不监控 ");
			return true;
		} else {
			return false;
		}
	}
	
	//根据当前系统时间，每周更新一次假日表的节日信息
	//@Scheduled(cron="0/10 * *  * * ? ")
	//https://blog.csdn.net/qq_33556185/article/details/51852537
	@Scheduled(cron= "0 15 10 15 * ?")
	public void updateHolidayByCurrentSystem() throws Exception{
		Calendar rightNow = Calendar.getInstance(); 
		rightNow.setTime(new Date());  
		int year  = rightNow.get(Calendar.YEAR);
		DateFormat df = DateFormat.getDateInstance();  
		Holiday holiday = new Holiday();
		holidayDao.delete();
		//http://www.sse.com.cn/disclosure/dealinstruc/closed/
		/*String str = httpClientService.doGet("http://www.sse.com.cn/disclosure/dealinstruc/closed/");
		System.out.println(str);*/
		Document doc = Jsoup.connect("http://www.sse.com.cn/disclosure/dealinstruc/closed/").get();
		/*doc = Jsoup.connect("http://blog.csdn.net/roy_70")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                .get();*/
		
	/*	Document doc = Jsoup.connect("http://blog.csdn.net/roy_70")
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();*/
		
		Elements trs = doc.getElementsByClass("table").select("tr");  ;
		 for (int i = 0; i < trs.size(); ++i) {  
		        // 获取一个tr  
		        Element tr = trs.get(i);  
		        // 获取该行的所有td节点  
		        Elements tds = tr.select("td");  
		        // 选择某一个td节点  
		        for (int j = 0; j < tds.size(); ++j) {  
		            Element td = tds.get(j);  
		           if(td.text().contains("节")||td.text().contains("旦")){
		        	// System.out.println(td.text().substring(0, td.text().length()-1));
		        	   String holidayName = td.text().substring(0, td.text().length()-1);
		        	   holiday.setHolidayName(holidayName);
		        	  // System.out.println(holidayName);
		           }else{
		        	   if(td.text().contains("至")){
		        		  String from =  td.text().split("至")[0].substring(0,td.text().split("至")[0].indexOf("日")+1);
		        		  String end =  td.text().split("至")[1].substring(0, td.text().split("至")[1].indexOf("休市"));
		        		  
		        		  String begin_month = from.substring(0,from.indexOf("月"));
		        		  String begin_day = from.substring(from.indexOf("月")+1,from.indexOf("日"));
		        		  
		        		  String end_month = end.substring(0,end.indexOf("月"));
		        		  String end_day = end.substring(end.indexOf("月")+1,end.indexOf("日"));
		        		  
		        		  if(begin_month.length()<2){
		        			  begin_month ="0"+begin_month;
		        		  }
		        		  if(begin_day.length()<2){
		        			  begin_day ="0"+begin_day;
		        		  }
		        		  
		        		  if(end_month.length()<2){
		        			  end_month ="0"+end_month;
		        		  }
		        		  if(end_day.length()<2){
		        			  end_day ="0"+end_day;
		        		  }
		        		  
		        		  String earyDateStr = year+"-"+begin_month+"-"+begin_day;
		        		  String lateDateStr  = year+"-"+end_month+"-"+end_day;
		        		 
		        		  Date  earlydate = df.parse(earyDateStr);   
		        		  Date  latedate = df.parse(lateDateStr);   
		        		  int getDays = daysBetween(earlydate,latedate)+1;
		        		  //System.out.println(getDays + "       " + earlydate +"  "+ latedate);
		        		  for (int k = 0; k < getDays; k++) {
		        			  //将开始日期加上日期相差之后的时间,用于获取相差日期天数之后的日期
		        			  Date date3 = new Date(earlydate.getTime() + k * 24 * 60 * 60 * 1000);
		        			  holiday.setHoliday(date3);
			        		  holidayDao.saveHoliday(holiday);
						  }
		        		 
		        	   }else{
		        		  // System.out.println(td.text().substring(0,4));
		        		   String dataStr = td.text().substring(0,4);
		        		   String begin_month = dataStr.substring(0,dataStr.indexOf("月"));
			        	   String begin_day = dataStr.substring(dataStr.indexOf("月")+1,dataStr.indexOf("日"));
			        		  
			        		  if(begin_month.length()<2){
			        			  begin_month ="0"+begin_month;
			        		  }
			        		  if(begin_day.length()<2){
			        			  begin_day ="0"+begin_day;
			        		  }
			        		  String dateStr = year+"-"+begin_month+"-"+begin_day;
			        		  //System.out.println(dateStr);
			        		  Date  date = df.parse(dateStr);  
			        		  holiday.setHoliday(date);
			        		  holidayDao.saveHoliday(holiday);
		        	   }
		           }
		           
		        }  
		 }
	}
	
	
	//@Scheduled(cron = "0/15 * *  * * ? ") // 每10秒执行一次
	@Scheduled(cron = "0/20 * 9,10,11,13,14  ? * MON-FRI")
	public void task1() throws Exception {
		boolean flag = this.getIsBegin();
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			if (!flag) {
				List<Stock> list = stockDao.getAll();
				for (Stock sts : list) {
					Map map  = pingAnWeb.getStockInfo(sts);
					//System.out.println("=========>" +map);
					//file.info(map);
					login.info(map);
					//stdout.info(map);
					String name = (String) map.get("stockName");
					String prevClose = (String) map.get("prevClose");
					String newPrice = (String) map.get("newPrice");
					String maxPrice  = (String) map.get("maxPrice");
					String minPrice  = (String) map.get("minPrice");
					String rase  = (String) map.get("risePrice");
					sts.setName(name);
					sts.setMaxPrice(maxPrice);
					sts.setMinPrice(minPrice);
					sts.setPrice(newPrice);
					sts.setRase(rase);
					sts.setPreClosePrice(prevClose);
					String pointerPrice = sts.getPointerPrice();
					sts.setPointerPrice(pointerPrice);
					stockDao.update(sts);
					
					StockTradeDeal trade = new StockTradeDeal();
					trade.setName(name);
					trade.setPrice(newPrice);
					trade.setStockCode(sts.getStockCode());
					trade.setStockCode(sts.getStockCode());
					stockTradeDealDao.save(trade);
				}
			}
		}
		
	}
	
	
	
	
	//@Scheduled(cron = "0 50 16 ? * MON-FRI") 
	@Scheduled(cron = "0 35 15 ? * MON-FRI") 
	public void updateStockDayInfo() throws Exception {
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
				List<Stock> list = stockDao.getAll();
				for (Stock sts : list) {
					Map map = pingAnWeb.getStockInfo(sts);
					String name = (String) map.get("stockName");
					String prevClose = (String) map.get("prevClose");
					String newPrice = (String) map.get("newPrice");
					String maxPrice = (String) map.get("maxPrice");
					String minPrice = (String) map.get("minPrice");
					String rase  = (String) map.get("risePrice");
					sts.setName(name);
					sts.setMaxPrice(maxPrice);
					sts.setMinPrice(minPrice);
					sts.setPrice(newPrice);
					sts.setRase(rase);
					sts.setPreClosePrice(prevClose);
					String pointerPrice = sts.getPointerPrice();
					sts.setPointerPrice(pointerPrice);
					stockDao.update(sts);

					StockDayInfo day = new StockDayInfo();
					day.setName(name);
					day.setPreClosePrice(prevClose);
					day.setStockCode(sts.getStockCode());
					day.setMaxPrice(maxPrice);
					day.setMinPrice(minPrice);
					day.setPrice(newPrice);
					day.setPreClosePrice(prevClose);
					stockDayInfoDao.save(day);
			}
		}
	}
	
	
	// send email
	//https://blog.csdn.net/qq_28663043/article/details/70153890
	//https://blog.csdn.net/csdn_xuexiaoqiang/article/details/73730649
	//http://www.jb51.net/article/128400.htm
	//spring_mvc http://www.jb51.net/article/111497.htm
	//@Scheduled(cron = "0 30 20 ? * MON-FRI") 
	//https://blog.csdn.net/nba276868534/article/details/52087705
	//https://blog.csdn.net/rookie_li/article/details/13627613#
	//@Scheduled(cron="0 0/3 *  * * ? ")   //每10秒执行一次
	@Scheduled(cron = "0 0 21  ? * MON-FRI ")
	public void sendDayInfo() throws Exception {
		boolean flag = this.getIsBegin();
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			if (!flag) {
				List<Stock> list = stockDao.getAll();
				MailModel mail = new MailModel();
				mail.setSubject("清单"+Math.random());
				StringBuilder sb = new StringBuilder();
				
				mail.setToEmails(emailAddressDao.getEmailAddress().getEmailAdd());
				//mail.setToEmails("2039288191@qq.com");
				sb.append("<table style='width:100%; border-collapse:collapse; margin:0 0 10px' cellspacing='0' border='0' cellpadding='0' cellspacing='0' >");
				sb.append("<tbody><tr>");
				sb.append("<td>");
				sb.append("<table cellspacing='0' border='0' cellpadding='0' cellspacing='0'>");
				int index =0;
					sb.append("<tr  style='font-size:14px;text-align:center;border:1px solid #C1D9F3'><td width='10%'>代码</td><td width='20%'>名称</td><td width='10%'>昨收/当前</td><td width='10%'>提示价</td><td width='10%'>连涨天数</td><td width='10%'>走势图</td></tr>");
					for (Stock sts : list) {
						int count = 0;
						index++;
						List<StockDayInfo> dayInfo= stockDayInfoDao.getStockDayInfo(sts.getStockCode());
						System.out.println(dayInfo);
						Double today = Double.valueOf(sts.getPrice());
						Double yesterday = Double.valueOf(sts.getPreClosePrice());
						if(index%2==0){
							sb.append("<tr style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'><td>"+sts.getStockCode()+"</td><td>"+sts.getName()+"</td><td>"+sts.getPreClosePrice()+"/"+sts.getPrice()+"</td><td>"+sts.getPointerPrice()+"</td><td>"+count+"</td><td></td></tr>");
						}else{
							sb.append("<tr style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'><td>"+sts.getStockCode()+"</td><td>"+sts.getName()+"</td><td>"+sts.getPreClosePrice()+"/"+sts.getPrice()+"</td><td>"+sts.getPointerPrice()+"</td><td>"+count+"</td><td></td></tr>");
						}
						
					}
					sb.append("</table>");
					sb.append("</td>");
					sb.append("</tbody></tr>");
					sb.append("</table>");
					String content = sb.toString();
					mail.setContent(content);
					emailServiceImpl.sendEmail(mail);
			}
	     }
	}
	
	
	
	
	 
	@Scheduled(cron="0/1 * *  * * ? ")
	public void updateStockJianChen(){
		List<Stock> list = stockDao.getNoPinYinStock();
		for (Stock sts : list) {
			if(sts.getName().contains("Ａ")){
				PinyinUtilsPro pro = new PinyinUtilsPro();
				pro.convertChineseToPinyin(sts.getName().substring(0,sts.getName().indexOf("Ａ")));
				String headP = pro.getHeadPinyin();
				sts.setPinyin(headP);
			}else if(!sts.getName().contains("Ａ")&&sts.getName().contains("ST")){
				PinyinUtilsPro pro = new PinyinUtilsPro();
				pro.convertChineseToPinyin(sts.getName().substring(sts.getName().indexOf("ST")+1, sts.getName().length()));
				String headP = pro.getHeadPinyin();
				sts.setPinyin(headP);
			}else{
				//System.out.println("====" +sts.getName());
				PinyinUtilsPro pro = new PinyinUtilsPro();
				pro.convertChineseToPinyin(sts.getName());
				String headP = pro.getHeadPinyin();
				sts.setPinyin(headP);
			}
			//System.out.println(sts.toString());
			
			stockDao.update(sts);
		}
	}
	
	
	
	//计算两个日期相差天数
	//https://www.cnblogs.com/mingforyou/p/3545174.html
	public static final int daysBetween(Date early, Date late) { 
	     
        java.util.Calendar calst = java.util.Calendar.getInstance();   
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        calst.setTime(early);   
         caled.setTime(late);   
         //设置时间为0时   
         calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         calst.set(java.util.Calendar.MINUTE, 0);   
         calst.set(java.util.Calendar.SECOND, 0);   
         caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         caled.set(java.util.Calendar.MINUTE, 0);   
         caled.set(java.util.Calendar.SECOND, 0);   
        //得到两个日期相差的天数   
         int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst   
                .getTime().getTime() / 1000)) / 3600 / 24;   
         
        return days;   
   } 
	
	
	
	
	
	
	
	
	
	
	/*@Scheduled(cron="0/4 * *  * * ? ")
	public void down() throws IOException {

		URL url = new URL("http://www.baidu.com");// 取得资源对象
		URLConnection uc = url.openConnection();// 生成连接对象
		uc.setDoOutput(true);
		uc.connect(); // 发出连接
		String temp;
		final StringBuffer sb = new StringBuffer();
		final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "gb2312"));
		while ((temp = in.readLine()) != null) {
			sb.append("\n");
			sb.append(temp);
		}
		in.close();
		System.out.println(sb);

	}*/
	
	//https://blog.csdn.net/airujingye/article/details/53041183
	@Scheduled(cron="0 0/1 *  * * ? ")
	public void test01() throws Exception{
		List <WebInfo> list = getWebInfoMapper.getWebInfo();
		System.out.println(list);
		if(list!=null&&list.size()>0){
			for (WebInfo webs : list) {
				String str = httpClientService.doGet(webs.getUrl(),"utf-8");
				savaFile(UUIDTools.getUUID(), str, "UTF-8");
			}
		}
	}
	
	
	 public String getDateFormat(){
		 	Date d = new Date();  
	        System.out.println(d);  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");  
	        String dateNowStr = sdf.format(d);  
	        return dateNowStr;
	    }  
	//https://yq.aliyun.com/ziliao/13578
	 //https://www.cnblogs.com/yezhenhan/archive/2012/09/10/2678690.html
	public  void savaFile(String fileName, String content, String format) {
		BufferedWriter rd = null;
		OutputStream out = null;
		File direct = new File("c:\\test\\" + getDateFormat()+"\\");
		String fileSavePath = direct+"\\" + fileName;
		File file = new File(fileSavePath+".html");
		
		FileInfo info = new FileInfo();
		info.setFile_ext(".html");
		info.setFilename(fileName);
		info.setFileDirect(direct.toString());
		info.setFileSavePath(fileSavePath);
		
		fileInfoMapper.save(info);
		
		try {
			if (!direct.exists()) {
				direct.mkdirs();
			}
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			
			
			out = new FileOutputStream(file);
			rd = new BufferedWriter(new OutputStreamWriter(out, format));
			rd.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != rd) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	
	//https://jingyan.baidu.com/article/27fa73265286c446f8271fc2.html
	//@Scheduled(cron= "0 15 10 20 * ?")
	@Scheduled(cron="0 0/15 *  * * ? ")
	public void deleteFileTask(){
		List<FileInfo> list = fileInfoMapper.getFile();
		if(list!=null&&list.size()>0){
			for (FileInfo fileInfo : list) {
				String pathName = fileInfo.getFileDirect();
				fileInfoMapper.delete(fileInfo);
				File file = new File(pathName);
				deleteFolder(file);
			}
			
		}
		
	}
	
	
	
	public void deleteFolder(File folder){
		File [] files = folder.listFiles();
		if(files!=null){
			for (File f : files) {
				if(f.isDirectory()){
					deleteFolder(f);
				}else{
					f.delete();
				}
			}
		}
		folder.delete();
	}
	
}
