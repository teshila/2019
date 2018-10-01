package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.Email;

public interface EmailDao {

	public List<Email> selectAll(Map map);
}
