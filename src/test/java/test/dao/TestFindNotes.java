package test.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tedu.cloudnote.dao.NoteDAO;
import com.tedu.cloudnote.entity.Note;

import test.BaseTest;

public class TestFindNotes extends BaseTest{
	
	@Test
	public void test() {
		NoteDAO dao = ac.getBean("noteDAO",NoteDAO.class);
		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("title", "%笔记%");
		params.put("status", "1");
		String s1 = "2017-07-01";
		Date beginDate = Date.valueOf(s1);
		Long begin = beginDate.getTime();
		List<Note> notes = dao.findNotes(params);
		for(Note note : notes) {
			System.out.println(note.getCn_note_title());
		}
		System.out.println("记录数："+notes.size());
	}
}
