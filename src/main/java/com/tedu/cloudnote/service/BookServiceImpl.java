package com.tedu.cloudnote.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.BookDAO;
import com.tedu.cloudnote.entity.Book;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService{
	@Resource
	private BookDAO dao;
	
	public NoteResult deleteBook(String bookId) {
		NoteResult rst = new NoteResult();
		int rows = dao.delete(bookId);
		if(rows == 1) {
			rst.setStatus(0);
			rst.setMsg("笔记本删除成功");
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("笔记本删除名失败");
		return rst;
	}
	//笔记本重命名
	public NoteResult renameBook(String bookId, String bookName) {
		Book book = new Book();
		book.setCn_notebook_id(bookId);
		book.setCn_notebook_name(bookName);
		int rows = dao.modify(book);
		NoteResult rst = new NoteResult();
		if(rows == 1) {
			rst.setStatus(0);
			rst.setMsg("笔记本重命名成功");
			rst.setData(bookName);
			return rst;
		}
		rst.setStatus(1);
		rst.setMsg("笔记本重命名失败");
		return rst;
	}
	
	public NoteResult addBook(String userId, String bookName) {
		Book book = new Book();
		book.setCn_user_id(userId); //设置用户ID
		book.setCn_notebook_name(bookName); //设置笔记本名
		String bookId = NoteUtil.createId();
		book.setCn_notebook_id(bookId); //设置笔记本ID
		book.setCn_notebook_type_id("5"); //设置笔记本类型
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		book.setCn_notebook_createtime(createTime); //设置笔记创建时间
		int rows = dao.save(book);
		NoteResult rst = new NoteResult();
		if(rows == 1) {
			rst.setStatus(0);
			rst.setMsg("创建笔记本成功");
			rst.setData(book);
			return rst;
		} else {
			rst.setStatus(1);
			rst.setMsg("创建笔记本失败");
			return rst;
		}
	}

	public NoteResult loadBooks(String userId) {
		List<Book> books = dao.findByUserId(userId);
		NoteResult rst = new NoteResult();
		
		rst.setStatus(0);
		rst.setMsg("加载笔记本成功");
		rst.setData(books);
		return rst;
	}

}
