package cn.itcast.shop.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.dao.ProductDao;
import cn.itcast.shop.dao.impl.ProductDaoImpl;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findHotProductList() {
		ProductDao productDao = new ProductDaoImpl();
		List<Product> hotProducts = null;
		try {
			hotProducts = productDao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProducts;
	}

	@Override
	public List<Product> findnewProductList() {
		ProductDao productDao = new ProductDaoImpl();
		List<Product> newProducts = null;
		try {
			newProducts = productDao.findnewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProducts;
	}

	@Override
	public PageBean<Product> findproductsByCid(String cid, int currentPage,
			int currentCount) {

		ProductDao productDao = new ProductDaoImpl();

		// 封装一个pageBean返回web层
		PageBean<Product> pageBean = new PageBean<Product>();
		if (currentPage == 0)
			currentPage = 1;
		if (currentCount == 0)
			currentCount = 12;
		// 封装当前页
		pageBean.setCurrentCount(currentCount);
		// 封装当前页显示条数
		pageBean.setCurrentPage(currentPage);
		// 封装总条数
		int totalCount = 0;
		try {
			totalCount = productDao.getCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		// 分装总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);
		// 当前页的数据
		int index = (currentPage - 1) * currentCount;
		List<Product> list = null;
		try {
			list = productDao.findnewProductPage(cid, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 封装数据
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Product findProductByPid(String pid) {
		ProductDao productDao = new ProductDaoImpl();
		Product p = null;
		try {
			p = productDao.findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public List<Product> getAllProducts() {
		ProductDao productDao = new ProductDaoImpl();
		List<Product> list = null;
		try {
			list = productDao.getAllProducts();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
