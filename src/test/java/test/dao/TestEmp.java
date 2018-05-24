package test.dao;

import org.junit.Test;

import com.tedu.cloudnote.dao.EmpDAO;
import com.tedu.cloudnote.entity.Emp;

import test.BaseTest;

public class TestEmp extends BaseTest{
	
	@Test
	public void test() {
		EmpDAO dao = ac.getBean("empDAO",EmpDAO.class);
		Emp emp = new Emp();
		emp.setName("lily");
		dao.save(emp);
		System.out.println(emp.getNo());
	}
}
