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
}