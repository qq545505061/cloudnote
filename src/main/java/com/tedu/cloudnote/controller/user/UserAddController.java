package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.BookService;
import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/user")
public class UserAddController {

	@Resource
	private UserService service;
	@Resource
	private BookService bookService;
	
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute(String name, String nick, String password) {
		NoteResult rst = service.addUser(name, nick, password);
		return rst;
	}
	
}
