package cn.itcast.shop.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.shop.dao.UserDao;
import cn.itcast.shop.domain.User;
import cn.itcast.shop.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public int register(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert  into  user  values(?,?,?,?,?,?,?,?,?,?)";

		return qr.update(sql, user.getUid(), user.getUsername(),
				user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(),
				user.getState(), user.getCode());
	}

	@Override
	public User login(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select  *  from  user  where  state=1  and  username =?  and password=?";
		return qr.query(sql, new BeanHandler<User>(User.class), username,
				password);
	}

	@Override
	public User checkUser(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String  sql="select  *  from  user  where 1=1 and  username =?";
		return qr.query(sql, new BeanHandler<User>(User.class),username);
	}

	@Override
	public int active(String userName, String code) throws SQLException {
		 QueryRunner  qr=new QueryRunner(DataSourceUtils.getDataSource());
		 String  sql="update  user   set  state=1  where username=? and  code=?";
		return  qr.update(sql, userName,code);
	}
}
