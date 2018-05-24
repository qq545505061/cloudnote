package com.tedu.cloudnote.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tedu.cloudnote.dao.UserDAO;
import com.tedu.cloudnote.entity.User;
import com.tedu.cloudnote.util.NoteException;
import com.tedu.cloudnote.util.NoteResult;
import com.tedu.cloudnote.util.NoteUtil;
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDAO")
	private UserDAO dao;

	public NoteResult changePassword(String userName, String password, String last_password) {
		try {
			NoteResult rst = new NoteResult();
			User user = dao.findByName(userName);
			String u_pwd = user.getCn_user_password();
			String md_pwd = NoteUtil.md5(last_password);
			if(md_pwd.equals(u_pwd)) {
				User user1 = new User();
				user1.setCn_user_name(userName);
				user1.setCn_user_password(NoteUtil.md5(password));
				int rows = dao.modify(user1);
				if(rows == 1) {
					rst.setStatus(0);
					rst.setMsg("修改用户密码成功");
					return rst;
				}
			}
			rst.setStatus(1);
			rst.setMsg("修改密码失败");
			return rst;
		} catch(Exception e) {
			throw new NoteException("修改用户密码异常！", e);
		}
	}
	
	public NoteResult addUser(String name, String nick, String password) {	
		NoteResult rst = new NoteResult();
		try {
			//检测是否重名
			User hasUser = dao.findByName(name);
			if(hasUser != null) {
				rst.setStatus(1);
				rst.setMsg("用户名被占用");
				return rst;
			}
			User user = new User();
			user.setCn_user_name(name);//设置用户名
			user.setCn_user_nick(nick);//设置妮称
			String userId = NoteUtil.createId(); //生成用户ID
			user.setCn_user_id(userId);// 设置用户ID
			String md5_pwd = NoteUtil.md5(password); //将明文密码转换成密文
			user.setCn_user_password(md5_pwd);//设置密码
			dao.save(user);
			//创建返回结果
	
			rst.setStatus(0);
			rst.setMsg("注册成功");
			user.setCn_user_password(""); //把密码屏蔽不返回
			rst.setData(user); //返回user信息
			return rst;
		} catch (Exception e) {
			throw new NoteException("用户注册异常", e);
		}
	}

	public NoteResult checkLogin(String name, String password) {
		NoteResult rst = new NoteResult();
		User user = dao.findByName(name);
		if (user == null) {
			rst.setStatus(1);
			rst.setMsg("用户名不存在");
			return rst;
		}
		// 将用户输入的明文加密
		try {
			password = NoteUtil.md5(password);
			if (!user.getCn_user_password().equals(password)) {
				rst.setStatus(2);
				rst.setMsg("密码不正确");
				return rst;
			}
		} catch (Exception e) {
			throw new NoteException("密码加密异常", e);
		}
		rst.setStatus(0);
		rst.setMsg("登录成功");
		rst.setData(user);
		return rst;
	}
}
