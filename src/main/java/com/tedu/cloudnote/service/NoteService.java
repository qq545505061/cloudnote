package com.tedu.cloudnote.service;

import com.tedu.cloudnote.entity.Note;
import com.tedu.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult searchNotes(String title, String status, String begin, String end);
	public NoteResult loadShareNote(String noteId);
	public NoteResult replayNote(String noteId, String bookId);
	public NoteResult deleteRollbackNote(String noteId);
	public NoteResult searchShareNote(String keyword, int page);
	public NoteResult shareNote(String noteId);
	public NoteResult loadRollbackNote(String userId);
	public NoteResult moveNote(String noteId, String bookId);
	public NoteResult deleteNote(String noteId);
	public NoteResult addNote(String userId, String bookId, String title);
	public NoteResult loadBookNotes(String bookId);
	public NoteResult loadNote(String noteId);
	public NoteResult modifyNote(String noteId, String title, String body);
}
