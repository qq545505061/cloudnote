package test.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

import test.BaseTest;

public class TestAddUser extends BaseTest{

	@Test
	public void test() {
		UserService service = ac.getBean("userService",UserService.class);
		NoteResult rst = service.addUser("demo1", "wq", "10101010");
		System.out.println(rst.getMsg());
	}
}
