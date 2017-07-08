package cn.itcast.shop.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.shop.dao.CategoryDao;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAll() throws SQLException {
		String sql = "select  *  from    category";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public Category findCategoryByCid(String cid) throws SQLException {
		String sql = "select  *  from  category  where  cid =?";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
	}

	public int delCategoryByCid(String cid) throws SQLException {
		String sql = "delete  from  category  where  cid=?";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		return qr.update(sql, cid);
	}

	public int updateCategoryByCid(Category category) throws SQLException {
		String sql="update category set cname=?  where  cid=?";
		QueryRunner  qr=new QueryRunner(DataSourceUtils.getDataSource());
		return qr.update(sql,category.getCname(),category.getCid());
	}
}
