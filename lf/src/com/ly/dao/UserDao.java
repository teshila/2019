package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.User;

public interface UserDao {

	public int save(User user);

	public User find(Map map);

	public List<User> findList(Map map);
}
