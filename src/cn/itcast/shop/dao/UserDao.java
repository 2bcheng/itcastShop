package cn.itcast.shop.dao;

import java.sql.SQLException;

import cn.itcast.shop.domain.User;

public interface UserDao {

	int register(User user) throws SQLException;

	User login(String username, String password) throws SQLException;

	User checkUser(String username) throws SQLException;

	int active(String userName, String code) throws  SQLException;

}
