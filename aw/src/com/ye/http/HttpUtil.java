package com.ye.http;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
@Component
public class HttpUtil {
	public static Logger log = LogManager.getLogger(HttpUtil.class);
	
	
	//https://blog.csdn.net/qq_34199125/article/details/54862806
	
	//https://blog.csdn.net/qianlong2hao/article/details/50175939
	public  String doGet(String url,Map<String, String> params,String encode) throws URISyntaxException{
		
		String str = null;
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = HttpClients.custom().build();  //为了给头部添加信息
		  if(null != params){
	            URIBuilder builder = new URIBuilder(url);
	            for (Map.Entry<String, String> entry : params.entrySet()) {
	                builder.setParameter(entry.getKey(), entry.getValue());
	            }
	            url = builder.build().toString();
	        }
		  log.info("===================*********************=============");
		  log.info("**************执行GET请求，URL = {} \n\n"+ url+"\n");
		  log.info("===================*********************=============");
		try{
			//创建httpget
			HttpGet httpget = new HttpGet(url);
			
			/*RequestConfig config=RequestConfig.custom()
	                .setConnectTimeout(18000)
	                .setSocketTimeout(18000)
	                .build();
			httpget.setConfig(config);*/
			
			 RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD)  
		                .setConnectionRequestTimeout(180000).setConnectTimeout(180000).setSocketTimeout(180000). build();
			
			httpget.setHeader("accept", "*/*");
			httpget.setHeader("Connection", "keep-alive");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"); 
			httpget.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329 MicroMessenger/5.0.1"); 
			
			//执行get请求
			CloseableHttpResponse response = null;
			HttpEntity entity = null;
			try{
				response = httpclient.execute(httpget);
				if (response.getStatusLine().getStatusCode() == 200) {
	                if(encode == null){
	                    encode = "UTF-8";
	                }
	            }
				//获取响应实体
				 entity = response.getEntity();
				 System.out.println("=============>  YE ======> " +entity);
				//响应状态
				//System.out.println(response.getStatusLine());
				//log.log(Level.INFO,"HTTP请求状态 ===>>>>"+ response.getStatusLine()+",请求的地址 ====> " + url);
				if(entity != null){
					//内容长度
					//System.out.println("Response content length: " + entity.getContentLength());
					//响应内容
					//System.out.println("Response content: " + EntityUtils.toString(entity));
					str = EntityUtils.toString(entity,encode);
					System.out.println(str);
					
					log.info(str);
					//System.out.println(str);
				}else{
					return null;
				}
			}finally{
				response.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				httpclient.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return str;
	}

	
}
