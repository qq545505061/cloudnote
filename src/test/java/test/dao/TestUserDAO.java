package test.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tedu.cloudnote.dao.UserDAO;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.service.UserService;
import com.tedu.cloudnote.service.UserServiceImpl;
import com.tedu.cloudnote.util.NoteResult;

public class TestUserDAO {
	
	@Test
	public void test1() throws SQLException {
		//创建Spring容器
		String[] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		//获取DataSource
		DataSource ds = ac.getBean("ds",DataSource.class);
		Connection conn = ds.getConnection();
		System.out.println(conn);
		conn.close();
		//获取SqlSessionFactory
		SqlSessionFactory ssf = ac.getBean("ssf",SqlSessionFactory.class);
		System.out.println(ssf.openSession());
		UserDAO dao = ac.getBean("userDAO",UserDAO.class);
		User user = dao.findByName("demo");
		if(user == null) {
			System.out.println("用户不存在！");
		} else {
			System.out.println(user.getCn_user_password());
		}
	}
	
}
