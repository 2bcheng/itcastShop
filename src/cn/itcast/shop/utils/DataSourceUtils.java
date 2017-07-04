package cn.itcast.shop.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author liuhaicheng c3p0数据源相关工具类,提供了,获取数据源,获取数据库连接对象, 关闭数据库连接对象,事务 ,回滚事务等相关操作
 */
public class DataSourceUtils {
	// 私有化构造函数不能new创建此工具类
	private DataSourceUtils() {
	}

	// /创建c3p0数据源
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	// 使用threadload 管理连接池
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	// 获取数据源
	public static DataSource getDataSource() {
		return dataSource;
	}

	// 获取数据库连接对象

	public static Connection getConnection() throws SQLException {
		Connection conn = tl.get();
		if (conn == null) {
			conn = dataSource.getConnection();
			tl.set(conn);
		}
		return conn;
	}

	// 开启事务
	public static void startTransaction() throws SQLException {
		Connection conn = getConnection();
		if (conn != null) {
			// 设置自动提交为false,即为关闭事务
			conn.setAutoCommit(false);
		}

	}

	// 回滚事务
	public static void rollBack() throws SQLException {
		Connection conn = getConnection();
		if (conn != null) {
			conn.rollback();//回滚事务
		}
	}

	// 提交事务,并从threadlocl中释放资源

	public static void commit() throws SQLException {
		Connection conn = getConnection();
		if (conn != null) {
			conn.commit();// 事务提交
			conn.close();// 关闭连接
			tl.remove();// 移除资源
		}

	}
	
	//关闭连接资源
	public  static  void  closeConn() throws SQLException{
		Connection conn = getConnection();
		if(conn!=null){
			conn.close();//关闭连接
		}
	}
	
	
}
