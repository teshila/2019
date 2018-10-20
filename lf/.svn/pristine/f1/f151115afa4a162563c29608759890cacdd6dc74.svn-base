package com.ly.controller;

import java.util.HashMap;
//https://blog.csdn.net/qq_31454017/article/details/71108278    mysql 过多
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ly.dao.CommentDao;
import com.ly.dao.ContentDao;
import com.ly.pojo.Comment;
import com.ly.pojo.Content;
import com.ly.utils.UUIDUtil;

@Controller
public class ContentController {

	@Autowired
	private ContentDao contentDao;

	
	@Autowired
	private CommentDao commentDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list")
	@ResponseBody
	public List getList(String id) {
		Map map = new HashMap();
		if (id != null) {
			map.put("cid", id);
		}
		List<Content> list = contentDao.findList(map);

		return list;
	}

	@RequestMapping("/saveArticle")
	@ResponseBody
	public Map save(Content content) {
		if(content==null||content.getCid()==null){
			content.setCid(UUIDUtil.getUUID());
		}
		
		Map map = new HashMap();
		try {
			map.put("code", "1");
			map.put("msg", "添加成功");
			contentDao.save(content);
		} catch (Exception e) {
			map.put("code", "0");
			map.put("msg", "系统繁忙，添加失败");
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	@RequestMapping("/saveComment")
	@ResponseBody
	public Map saveComment(Comment comment) {
		Map map = new HashMap();
		if(comment==null||comment.getCid()==null){
			comment.setCid(UUIDUtil.getUUID());
		}
		try {
			map.put("code", "1");
			map.put("msg", "添加成功");
			commentDao.save(comment);
		} catch (Exception e) {
			map.put("code", "0");
			map.put("msg", "系统繁忙，添加失败");
			e.printStackTrace();
		}
		return map;
	}
}
