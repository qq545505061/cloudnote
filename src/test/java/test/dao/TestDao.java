package test.dao;

import org.junit.Test;

import com.tedu.cloudnote.dao.AssociationDAO;
import com.tedu.cloudnote.dao.BookDAO;
import com.tedu.cloudnote.dao.CollectionDAO;
import com.tedu.cloudnote.dao.NoteDAO;
import com.tedu.cloudnote.entity.Book;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.service.BookService;
import com.tedu.cloudnote.service.NoteService;

import test.BaseTest;

public class TestDao extends BaseTest{
	
	@Test
	public void test5() {
		CollectionDAO dao = ac.getBean("collectionDAO",CollectionDAO.class);
		User user = dao.findById("48595f52-b22c-4485-9244-f4004255b972");
		System.out.println("用户名："+user.getCn_user_name());
		for(Book book : user.getBooks()) {
			System.out.println("拥有的笔记本："+book.getCn_notebook_name());
		}
		
	}
	
	@Test
	public void test4() {
		AssociationDAO dao = ac.getBean("associationDAO",AssociationDAO.class);
		Book book = dao.findById2("f744e502346246e0aba1911841ab49e6");
		System.out.println("笔记本名："+book.getCn_notebook_name());
		System.out.println("所属用户名："+book.getUser().getCn_user_name());
	}
	
	@Test
	public void test3() {
		NoteDAO dao = ac.getBean("noteDAO",NoteDAO.class);
		String[] ids = {"ffc2cf21-78ed-4647-adb4-3e545613ef26","f4594f33-06d4-47dc-87cf-c3bd20e5a23f","f3ad58a7-eb14-4766-90b2-25d5fd22113b"};
		int rows = dao.batchDelete(ids);
		System.out.println("删除记录输："+rows);
		
	}

	@Test
	public void test() {
		BookDAO dao = ac.getBean("bookDAO",BookDAO.class);
		NoteDAO noteDao = ac.getBean("noteDAO",NoteDAO.class);
		BookService bookService = ac.getBean("bookService",BookService.class);
		NoteService noteService = ac.getBean("noteService",NoteService.class);
		
//		List<Book> book = dao.findByUserId("demo");
//		if(book == null) {
//			System.out.println("没有这个笔记本");
//		}
//		System.out.println(book);
//		Book book = new Book();
//		book.setCn_user_id("48595f52-b22c-4485-9244-f4004255b972");
//		book.setCn_notebook_name("我的笔记本");
//		book.setCn_notebook_id(NoteUtil.createId());
//		int rows = dao.save(book);
//		System.out.println(rows);
//		List<Book> list = dao.findAllBook();
//		for(Book b : list) {
//			System.out.println(b.getCn_notebook_name());
//		}

//		List<Map> notes = noteDao.findByBookId("fa8d3d9d-2de5-4cfe-845f-951041bcc461");
//		System.out.println(notes);
//		NoteResult rst = noteService.loadBookNotes("fa8d3d9d-2de5-4cfe-845f-951041bcc461");
//		System.out.println(rst.getData());
		
	}
	
	@Test
	public void test2() {
		String a = "a";
		String b = "a";
		boolean c = (a.equals(b));
		System.out.println(c);
	}
}
