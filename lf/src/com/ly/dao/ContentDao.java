package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.Content;

public interface ContentDao {

	public int save(Content content);

	public Content find(Map map);

	public List<Content> findList(Map map);
}
