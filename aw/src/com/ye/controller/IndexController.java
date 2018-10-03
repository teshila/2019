package com.ye.controller;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value={"/index","/"})
	public String index(){
		return "menu";
	}
	
	@RequestMapping(value={"/add"})
	public String add(){
		return "add";
	}
	
	@RequestMapping(value={"/all"})
	public String all(){
		return "all";
	}
	
	@RequestMapping(value={"/sys"})
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		//获取浏览器信息
		Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
		//获取浏览器版本号
		Version version = browser.getVersion(request.getHeader("User-Agent"));
		String info = browser.getName() + "/" + version.getVersion();
		String osName = System.getProperty("os.name" );
		String osVersion=System.getProperty("os.version");
		System.out.println(osName);
		System.out.println(osVersion);
		System.out.println(info);
		return "sys";
	}
	
	
	@RequestMapping(value={"/toQuShi"})
	public String toQuShi(){
		return "toQuShi";
	}

}
