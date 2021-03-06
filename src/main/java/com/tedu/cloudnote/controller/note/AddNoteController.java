package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class AddNoteController {
	@Resource
	private NoteService noteService;
	
	@RequestMapping("/note/add.do")
	@ResponseBody
	public NoteResult execute(String userId, String bookId, String title) {
		NoteResult rst = noteService.addNote(userId, bookId, title);
		return rst;
	}

}
