package com.ly.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ly.pojo.Music;

public class LaoDaoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		Map map = null;
		List results  =  new ArrayList();
		Music m = null;
		resp.setHeader("Access-Control-Allow-Origin", "*");   
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");   
		resp.setHeader("Access-Control-Max-Age", "3628800");


		
		resp.setContentType("application/json;charset=utf-8");// 指定返回的格式为JSON格式
		String musicName = req.getParameter("m");
		PrintWriter out = resp.getWriter();
		if(musicName==null){
			out.write("{\"code\":\"参数不能为空\"}");
			out.close();
		}else{
			musicName = URLEncoder.encode(musicName, "utf-8");
			String str = LaoDaoInfoUtils.getMusicList(musicName);
			
			JSONObject  ob = JSONObject.parseObject(str);
			JSONObject dataObj = (JSONObject) ob.get("data");
			JSONObject dataObj2 = (JSONObject) dataObj.get("song");
			JSONObject musicThumbPartent = (JSONObject) dataObj.get("zhida");
			JSONObject thumbWrap = (JSONObject) musicThumbPartent.get("zhida_album");
			String thumb = null;
			if(thumbWrap!=null){
				thumb=  (String) thumbWrap.get("albumPic");
			}
			
			
			
			
			JSONArray dataObj3 = (JSONArray) dataObj2.get("list");
			for (int i = 0; i < dataObj3.size(); i++) {
				//System.out.println(dataObj3.size());
				JSONObject objInner = dataObj3.getJSONObject(i);
				//System.out.println("====歌区资源目录 ===》 " + objInner.get("file"));
				JSONObject musicFileName = (JSONObject) objInner.get("file");
				JSONObject itemThumbImg = (JSONObject) objInner.get("album");
				//System.out.println("====需要的资源名称为===》 " + musicFileName.get("media_mid"));
				map = new HashMap();
				
				//System.out.println("歌名   " +objInner.get("name"));
				JSONArray singerArray = (JSONArray) objInner.get("singer");
				//System.out.println(singerArray);
				/*for (int j = 0; j < singerArray.size(); j++) {
					JSONObject singerInfo = singerArray.getJSONObject(j);
					System.out.println("歌手  == > " +singerInfo.get("name"));
				}*/
				
				JSONObject singerInfo = singerArray.getJSONObject(0);
				//System.out.println("歌手  == > " +singerInfo.get("name"));
				
				String fileId = (String) musicFileName.get("media_mid");
				String itemPic = (String) itemThumbImg.get("mid");
				System.out.println(itemPic);
				
				String musicFile = "http://ws.stream.qqmusic.qq.com/C100"+fileId+".m4a?fromtag=0&guid=126548448";
				//String musicLrc = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?callback=MusicJsonCallback_lrc&pcachetime=1537790410130&songmid=000ukH6r3UehRh&g_tk=949041503&jsonpCallback=MusicJsonCallback_lrc&loginUin=3409304997&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
				String musicLrcURL = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?callback=MusicJsonCallback_lrc&pcachetime=1537790410130&songmid="+fileId+"&g_tk=949041503&jsonpCallback=MusicJsonCallback_lrc&loginUin=3409304997&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0";
				
				
				String musicLrc = LaoDaoInfoUtils.getMusicLrc(musicLrcURL);
				
				JSONObject lrcObj = JSONObject.parseObject(musicLrc);
				//System.out.println(lrcObj.get("lyric"));
				String ls = (String) lrcObj.get("lyric");
				String lyc = null;
				if(ls!=null){
					//lyc= java.util.Base64.getEncoder().encodeToString(ls.getBytes());
					 byte[] decode = Base64.getDecoder().decode(ls);
				      System.out.println(new String(decode, "UTF-8"));
				       lyc =  new String(decode, "UTF-8");
				}
				
				map.put("src", musicFile );
				map.put("singer",  singerInfo.get("name"));
				map.put("title",  objInner.get("name"));
				map.put("lrc", lyc );
				map.put("thumb", thumb );
				//https://y.gtimg.cn/music/photo_new/T002R300x300M000000J3SU44DmPCP.jpg?max_age=2592000 =s
				//https://y.gtimg.cn/music/photo_new/T002R300x300M000000J3SU44DmPCP.jpg?max_age=2592000
				String pic = "https://y.gtimg.cn/music/photo_new/T002R300x300M000"+itemPic+".jpg?max_age=2592000";
				
				map.put("itemsPic", pic );
				
				/*m = new Music();
				m.setLrc(lyc);
				m.setSrc(musicFile);
				m.setSinger((String)singerInfo.get("name"));
				m.setTitle((String)objInner.get("name"));
				m.setThumbPic(thumb);
				String pic = "https://y.gtimg.cn/music/photo_new/T002R300x300M000"+itemPic+".jpg?max_age=2592000";
				m.setItemPic(pic);*/
				
				
				results.add(map);
				
			}
			
			//System.out.println(map);
			
			
			
			if(str!=null){
				//out.write(str);
				String ret = JSONArray.toJSONString(results);
				out.write(ret);
			}else{
				out.write("{\"msg\":\"无数据\"}");
			}
			out.close();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
