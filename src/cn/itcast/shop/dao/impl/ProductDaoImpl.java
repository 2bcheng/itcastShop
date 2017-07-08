package cn.itcast.shop.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.shop.dao.ProductDao;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findHotProductList() throws SQLException {
		String sql = "select   *  from  product  where  is_hot=1   limit 0,9 ";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findnewProductList() throws SQLException {

		String sql = "select  *  from  product   order by pdate desc  limit 0,9 ";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public int getCount(String cid) throws SQLException {
		String sql = "select  count(*)  from  product where  cid=?";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		Long count = (Long) qr.query(sql, new ScalarHandler(), cid);
		return count.intValue();
	}

	@Override
	public List<Product> findnewProductPage(String cid, int index,
			int currentCount) throws SQLException {
		String sql = "select  *  from  product  where  cid=?  limit ?,?";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		return qr.query(sql, new BeanListHandler<Product>(Product.class), cid,
				index, currentCount);
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		String sql = "select  *  from  product  where pid=?";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public List<Product> getAllProducts() throws SQLException {
		String sql = "select  *  from  product  ";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

}
