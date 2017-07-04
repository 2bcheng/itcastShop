package cn.itcast.shop.service;

import java.sql.SQLException;

import cn.itcast.shop.domain.User;

public interface UserService {

	boolean register(User user) ;

	User login(String username, String password);

	boolean checkUser(String username) throws  SQLException;

	boolean  active(String userName, String code)throws  SQLException;

}
