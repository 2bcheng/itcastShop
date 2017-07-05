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

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.service.ProductService;
import cn.itcast.shop.service.impl.CategoryServiceImpl;
import cn.itcast.shop.service.impl.ProductServiceImpl;

public class ProductServlet extends BaseServlet {

	private Logger log = LogManager.getLogger(ProductServlet.class);

	// 准备调用热门商品和最新商品
	public void index(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ProductService productService = new ProductServiceImpl();
		// 热门商品
		List<Product> hotProducts = productService.findHotProductList();
		// 最新商品
		List<Product> newProducts = productService.findnewProductList();

		// 准备分类数据
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.getAll();

		request.setAttribute("categories", categories);
		request.setAttribute("hotProducts", hotProducts);
		request.setAttribute("newProducts", newProducts);
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

	// 根据类别查询商品
	public void productsByCid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取cid
		String cid = request.getParameter("cid");
		// 获取当前页面,如果没有为赋值为1
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null)
			currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		// 当前页总条数
		int currentCount = 12;

		// 查询数据
		ProductService productService = new ProductServiceImpl();
		PageBean<Product> pageBean = productService.findproductsByCid(cid,
				currentPage, currentCount);
		// 日志记录
		log.info(pageBean.toString());
		log.info(cid);
		// 设置作用域
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		// 转发
		request.getRequestDispatcher("/product_list.jsp").forward(request,
				response);
	}

	public void productByPid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//接收传递的参数
		String pid = request.getParameter("pid");
		//创建service对象
		ProductService productService = new ProductServiceImpl();
		//生成商品对象
		Product product = null;
		//当传递的参数的值不为空
		if (pid.length() > 0) 
			//接收service层传递的数据
			product = productService.findProductByPid(pid);
		
		//日志记录当前对象
		log.info(product.toString());
		if (product != null) {
			//设置作用域
			request.setAttribute("product", product);
			//转发
			request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		}
		else{
			//查询不到
			log.warn("当前查询对象为空,请查询数据库");
		}

	}
}