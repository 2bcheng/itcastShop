package cn.itcast.shop.service.impl;

import java.sql.SQLException;

import cn.itcast.shop.dao.UserDao;
import cn.itcast.shop.dao.impl.UserDaoImpl;
import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao userDao = new UserDaoImpl();

	// 注册
	@Override
	public boolean register(User user) {
		int row = 0;
		try {
			row = userDao.register(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row > 0 ? true : false;
	}

	// 登录
	@Override
	public User login(String username, String password) {

		try {
			return userDao.login(username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 验证
	@Override
	public boolean checkUser(String username) throws SQLException {
		User user = null;
		user = userDao.checkUser(username);
		return user == null ? false : true;
	}

	// 激活
	@Override
	public boolean active(String userName, String code) throws SQLException {

		return userDao.active(userName, code) > 0 ? true : false;
	}

}
