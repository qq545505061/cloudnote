package com.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.BookService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class DeleteBookController {
	@Resource
	private BookService service;
	
	@RequestMapping("/book/delete.do")
	@ResponseBody
	public NoteResult execute(String bookId) {
		NoteResult rst = service.deleteBook(bookId);
		return rst;
	}
}
