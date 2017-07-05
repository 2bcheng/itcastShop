package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.service.impl.CategoryServiceImpl;
import cn.itcast.shop.utils.JedisUtils;

public class CategoryServlet extends BaseServlet {

	private Logger log = LogManager.getLogger(CategoryServlet.class);

	// 获取所有分类
	public void getAll(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		CategoryService categoryService = new CategoryServiceImpl();
		// 获取redis中的数据
		Jedis jedis = JedisUtils.getJedis();
		String categoryListToJson = jedis.get("categoryList");
		// 如果redis中不存在,在对数据库进行查询
		if (categoryListToJson == null) {
			List<Category> list = null;
			list = categoryService.getAll();
			categoryListToJson = JSON.toJSONString(list);
		}
		log.info(categoryListToJson);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(categoryListToJson);
	}
}