package test.dao;

import java.util.List;

import org.junit.Test;

import com.tedu.cloudnote.dao.CollectionDAO;
import com.tedu.cloudnote.entity.User;

import test.BaseTest;

public class TestCollectionDao extends BaseTest{
	
	@Test
	public void test() {
		CollectionDAO dao = ac.getBean("collectionDAO",CollectionDAO.class);
		List<User> users = dao.findAllUser();
		for(User user : users) {
			System.out.println("用户名："+user.getCn_user_name()+",拥有笔记本数："+user.getBooks().size());
			
		}
	}
}
