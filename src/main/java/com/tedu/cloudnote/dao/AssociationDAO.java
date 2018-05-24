package com.tedu.cloudnote.dao;

import com.tedu.cloudnote.entity.Book;

public interface AssociationDAO {
	public Book findById2(String bookId);
	public Book findById(String bookId);
}
