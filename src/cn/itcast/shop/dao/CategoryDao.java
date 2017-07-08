package cn.itcast.shop.dao;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.domain.Category;

public interface CategoryDao {
	public List<Category> getAll() throws SQLException;

	public int delCategoryByCid(String cid) throws SQLException;

	public Category findCategoryByCid(String cid) throws SQLException;

	public int updateCategoryByCid(Category category) throws SQLException;
}
