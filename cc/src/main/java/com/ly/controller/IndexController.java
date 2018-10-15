package com.ly.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping(value = { "/", "/index" })
	public String doIndex(HttpServletRequest request) {
		return "index";
	}

	@RequestMapping("/m")
	public String monintor(){
		return "monintor";
	}
	
	
	@RequestMapping("/c")
	public String chose(){
		return "choose";
	}
	
	@RequestMapping(value = "add")
	public String mo(HttpServletRequest request) {
		return "add";
	}

	@RequestMapping(value = "all")
	public String all(HttpServletRequest request) {
		return "all";
	}

	@RequestMapping(value = "zhang")
	public String list(HttpServletRequest request) {
		return "zhang";
	}
}
