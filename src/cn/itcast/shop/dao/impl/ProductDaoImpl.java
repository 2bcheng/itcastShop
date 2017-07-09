package cn.itcast.shop.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
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

	@Override
	public int add(Product product) throws SQLException {
		String sql = "insert  into  product VALUES(?,?,?,?,?,?,?,?,?,?)  ";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

		return qr.update(sql, product.getPid(), product.getPname(),
				product.getMarket_price(), product.getShop_price(),
				product.getPimage(), product.getPdate(), product.getIs_hot(),
				product.getPdesc(), product.getPflag(), product.getCid());
	}

	@Override
	public int getCount() throws SQLException {
		String sql = "select  count(*)  from  product   ";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		Long count = (Long) qr.query(sql, new ScalarHandler());
		return count.intValue();
	}

	@Override
	public List<Product> getAllProducts(int index, int currentCount,
			Product product) throws SQLException {

		List<String> list = new ArrayList<String>();
		
		
		StringBuilder sb = new StringBuilder(
				"select * from product where 1=1 ");
		if (product.getPname() != null
				&& product.getPname().trim().length() > 0) {
			sb.append("  and pname like  ?   ");
			list.add("%" + product.getPname().trim() + "%");
		}
		sb.append("        limit  ?,?");
		
		String  name="%"+product.getPname()+"%";
		
		
		System.out.println(sb.toString());
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		if (list.size() > 0) {

			return qr.query(sb.toString(), new BeanListHandler<Product>(
					Product.class),name,index, currentCount);
		} else {

			return qr.query(sb.toString(), new BeanListHandler<Product>(
					Product.class), index, currentCount);
		}

	}

}
