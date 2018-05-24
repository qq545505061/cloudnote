package test.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.util.NoteResult;

public class TestCheckLogin {
	
	@Test
	public void test1() {
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		UserService us = ac.getBean("userService", UserService.class);
		NoteResult rst = us.checkLogin("demo", "c8837b23ff8aaa8a2dde915473ce0991");
		System.out.println(rst.getMsg());
	}
}
