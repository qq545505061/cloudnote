package com.tedu.cloudnote.service;

import com.tedu.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult deleteBook(String bookId);
	public NoteResult renameBook(String bookId, String bookName);
	public NoteResult addBook(String userId, String bookName);
	public NoteResult loadBooks(String userId);
}
