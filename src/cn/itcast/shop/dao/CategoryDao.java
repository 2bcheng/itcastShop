package cn.itcast.shop.dao;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.domain.Category;

public interface CategoryDao {
	List<Category> getAll() throws SQLException;
}
