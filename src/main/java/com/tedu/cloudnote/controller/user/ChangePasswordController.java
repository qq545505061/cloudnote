package com.tedu.cloudnote.controller.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class ChangePasswordController {
	
	@Resource
	private UserService service;
	
	@RequestMapping("/user/changepwd.do")
	@ResponseBody
	public NoteResult execute(String userName, String password, String last_password) {
		NoteResult rst = service.changePassword(userName, password, last_password);
		return rst;
	}

}
