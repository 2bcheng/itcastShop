package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.service.ProductService;
import cn.itcast.shop.service.impl.CategoryServiceImpl;
import cn.itcast.shop.service.impl.ProductServiceImpl;

public class AdminServlet extends BaseServlet {
	private Logger log = LogManager.getLogger(AdminServlet.class);

	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService service = new CategoryServiceImpl();
		String cid = request.getParameter("cid");
		Category category = service.findCategoryByCid(cid);
		if (category != null) {
			request.setAttribute("cate", category);
			request.getRequestDispatcher("/admin/category/edit.jsp").forward(
					request, response);
			;
		}
	}

	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cid = request.getParameter("cid");
		// 删除
		// 完成后跳转到list
	}

	public void getAllCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		CategoryService service = new CategoryServiceImpl();
		List<Category> list = service.getAll();
		request.setAttribute("list", list);

		System.out.println(request.getContextPath()
				+ "/admin/category/list.jsp");
		log.info(list);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(
				request, response);
		;
	}

	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		CategoryService service = new CategoryServiceImpl();

		// 更新完成跳转到list

	}

	public void getAllProducts(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ProductService service = new ProductServiceImpl();
		List<Product> list = service.getAllProducts();
		if (list != null) {
			request.setAttribute("list", list);
			request.getRequestDispatcher("/admin/product/list.jsp").forward(
					request, response);
		}
	}

	public void editProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");

		ProductService service = new ProductServiceImpl();
		Product pro = null;
		if (pid != null) {
			 pro = service.findProductByPid(pid);
		//	 Category  =new CategoryServiceImpl().findCategoryByCid(pro.)
		}

	}

	public void delProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String pid = request.getParameter("pid");
		ProductService service = new ProductServiceImpl();
		List<Product> list = service.getAllProducts();
		if (list != null) {
			request.setAttribute("list", list);
			request.getRequestDispatcher("/admin/product/list.jsp").forward(
					request, response);
		}
	}
}