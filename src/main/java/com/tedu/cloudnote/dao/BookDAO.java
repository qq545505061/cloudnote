package com.tedu.cloudnote.dao;

import java.util.List;

import com.tedu.cloudnote.entity.Book;

public interface BookDAO {
	public int delete(String bookId);
	public int modify(Book book);
	public int save(Book book);
	public List<Book> findByUserId(String id);
}
