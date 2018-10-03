package com.ye.dao;

import com.ye.pojo.User;

public interface UserMapper {
	public void save(User user);
	public User getUser(String name);
}
