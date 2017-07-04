package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.service.impl.CategoryServiceImpl;

public class CategoryServlet extends HttpServlet {

	// 获取所有分类
	public void getAll(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = null;
		try {
			list = categoryService.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (list != null) {
			request.getSession().setAttribute("cateList", list);
			request.getRequestDispatcher("").forward(request, response);
		}
	}
}