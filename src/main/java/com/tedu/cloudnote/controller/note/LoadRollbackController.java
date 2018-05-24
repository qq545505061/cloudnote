package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class LoadRollbackController {
	@Resource
	private NoteService service;
	
	@RequestMapping("/note/rollback.do")
	@ResponseBody
	public NoteResult execute(String userId) {
		NoteResult rst = service.loadRollbackNote(userId);
		return rst;
	}
}
