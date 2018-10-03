package com.ly.info.temp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LaoDaoInfo extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String music = req.getParameter("key");
		resp.setContentType("application/json;charset=utf-8");// 指定返回的格式为JSON格式
		req.setCharacterEncoding("utf-8");
		System.out.println(music);
		PrintWriter out = resp.getWriter();
		if (music == null) {
			
			out.write("{\"code\":\"参数不能为空\"}");
			out.close();
		} else {
			music = URLEncoder.encode(music, "utf-8");
			System.out.println(music);
			String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.catalogSug&query="+music;
			String ret = loadJSON(url);
			System.out.println(ret);
			out.write(ret);
			out.close();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String key = "不怕不怕";
		String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.catalogSug&query="
				+ key;
		String json = loadJSON(url);
		
		System.out.println(json);
	}

	public static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "utf-8"));// 防止乱码
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}
}
