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
import com.ly.dao.UserDao;
import com.ly.pojo.Comment;
import com.ly.pojo.Content;
import com.ly.pojo.User;
import com.ly.utils.UUIDUtil;

@Controller
public class ContentController {

	@Autowired
	private ContentDao contentDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private UserDao userDao;

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
	public Map save(Content content, User user) {
		if (content == null || content.getCid() == null) {
			String uuid = UUIDUtil.getUUID();
			content.setCid(uuid);
			user.setUid(uuid);
			user.setPic("http://120.78.225.98/lf/images/default.png");
			user.setNickname("特别特别爱吃鱼的小花猫");
			content.setUser(user);
			userDao.save(user);
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
	public Map saveComment(Comment comment, Content content) {
		Map map = new HashMap();
		if (comment == null || comment.getComId() == null) {
			String uuid = UUIDUtil.getUUID();
			comment.setComId(uuid);
			User user = new User();
			user.setUid(uuid);
			content.setCid(uuid);
			comment.setContent(content);
			user.setPic("http://120.78.225.98/lf/images/default.png");
			user.setNickname("特别特别爱吃鱼的小花猫");
			comment.setUser(user);
		}
		try {
			map.put("code", "1");
			map.put("msg", "添加成功");
			commentDao.save(comment);
			
			Content contentDb = contentDao.find(map);
			
			content.setRedu(contentDb.getRedu()+1);
			content.setTotalCommentSum(contentDb.getTotalCommentSum() + 1);

			contentDao.save(content);

		} catch (Exception e) {
			map.put("code", "0");
			map.put("msg", "系统繁忙，添加失败");
			e.printStackTrace();
		}
		return map;
	}
}
