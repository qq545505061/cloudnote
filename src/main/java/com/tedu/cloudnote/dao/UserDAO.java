package com.tedu.cloudnote.dao;

import org.springframework.stereotype.Repository;

import com.tedu.cloudnote.entity.User;

public interface UserDAO {
	public int modify(User user);
	public User findByName(String name);
	public void save(User user);
}
