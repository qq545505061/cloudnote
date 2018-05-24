package com.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.service.NoteService;
import com.tedu.cloudnote.util.NoteResult;

@Controller
public class ModifyNoteController {
	@Resource(name="noteService")
	private NoteService service;
	
	
	@RequestMapping("/note/update.do")
	@ResponseBody
	public NoteResult execute(String noteId, String title, String body) {
		NoteResult rst = service.modifyNote(noteId, title, body);
		return rst;
	}

}
