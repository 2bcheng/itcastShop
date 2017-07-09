package cn.itcast.shop.dao;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;

public interface ProductDao {
	public List<Product> findHotProductList() throws SQLException;

	public List<Product> findnewProductList() throws SQLException;

	public int getCount(String cid) throws SQLException;
	public int getCount() throws SQLException;
	public List<Product> findnewProductPage(String cid, int index,
			int currentCount) throws SQLException;

	public Product findProductByPid(String pid) throws SQLException;

	public List<Product> getAllProducts() throws SQLException;

	public int add(Product product) throws SQLException;

	public List<Product> getAllProducts(int index, int currentCount,Product product) throws SQLException;
}
