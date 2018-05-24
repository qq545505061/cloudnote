package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.User;

public interface CollectionDAO {
	public List<User> findAllUser();
	public User findById(String userId);
}
