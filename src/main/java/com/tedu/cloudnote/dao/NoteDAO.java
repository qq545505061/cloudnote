package com.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import com.tedu.cloudnote.entity.Note;

public interface NoteDAO {
	public int batchDelete(String[] ids);
	public int dynamicUpdate(Note note);
	public List<Note> findNotes(Map params);
	public int delete(String noteId);
	public int modifyTypeId2(String noteId);
	public List<Map> findByStatusId2(String userId);
	public int move(Note note);
	public int updateStatus(Note note);
	public int save(Note note);
	public List<Note> findByBookId(String bookId);
	public Note findByNoteId(String noteId);
//	public int modify(Note note);
}
