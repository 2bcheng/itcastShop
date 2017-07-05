package cn.itcast.shop.service.impl;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.dao.CategoryDao;
import cn.itcast.shop.dao.impl.CategoryDaoImpl;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> getAll() {
		CategoryDao categoryDao = new CategoryDaoImpl();
		List<Category> list = null;
		try {
			list = categoryDao.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
