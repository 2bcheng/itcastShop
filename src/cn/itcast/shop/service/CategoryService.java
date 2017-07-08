package cn.itcast.shop.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.shop.domain.Category;

public interface CategoryService {
	List<Category> getAll() ;

	Category findCategoryByCid(String cid);
}
