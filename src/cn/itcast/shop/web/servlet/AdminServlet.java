package cn.itcast.shop.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Orders;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.service.ProductService;
import cn.itcast.shop.service.impl.CategoryServiceImpl;
import cn.itcast.shop.service.impl.OrderServiceImpl;
import cn.itcast.shop.service.impl.ProductServiceImpl;

public class AdminServlet extends BaseServlet {
	private Logger log = LogManager.getLogger(AdminServlet.class);

	public void findOrderInfoByOid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		List<Map<String, Object>> list = new OrderServiceImpl()
				.findOrderItemsByOid(oid);

	
		  String jsonString = JSON.toJSONString(list);
		  response.setContentType("text/html;charset=utf-8");
		  response.getWriter().write(jsonString);
	}

	public void getAllOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Orders> orders = new OrderServiceImpl().getAllOrders();

		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request,
				response);

	}

	public void updateProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		Product p = new Product();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			List<FileItem> parseRequest = fileUpload.parseRequest(request);

			for (FileItem item : parseRequest) {
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("utf-8");
					map.put(fieldName, fieldValue);
				} else {
					String name = item.getName();
					String path = this.getServletContext()
							.getRealPath("upload");
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(path + "/" + name);
					IOUtils.copy(in, out);
					in.close();
					out.close();
					item.delete();
					map.put("piamge", "upload/" + name);

				}

			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		try {
			BeanUtils.populate(p, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		// 商品实体类赋值完成,不完成daosql更新语句
	}

	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService service = new CategoryServiceImpl();
		String cid = request.getParameter("cid");
		Category category = service.findCategoryByCid(cid);
		if (category != null) {
			request.setAttribute("cate", category);
			request.getRequestDispatcher("/admin/category/edit.jsp").forward(
					request, response);
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

		Map<String, String[]> properties = request.getParameterMap();

		Product product = new Product();

		try {
			BeanUtils.populate(product, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		// 当前页
		String strCurrentPage = request.getParameter("currentPage");
		int currentPage = 1;
		if (strCurrentPage.length() > 0)
			Integer.parseInt(strCurrentPage);
		// 每页显示内容
		int currentCount = 12;
		PageBean<Product> pageBean = service.getAllProducts(currentCount,
				currentPage, product);

		List<Product> list = service.getAllProducts();
		if (list != null) {
			request.setAttribute("list", pageBean);
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

		}
		request.setAttribute("pro", pro);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(
				request, response);

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