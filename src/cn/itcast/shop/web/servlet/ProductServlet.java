package cn.itcast.shop.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.itcast.shop.domain.Cart;
import cn.itcast.shop.domain.CartItem;
import cn.itcast.shop.domain.Category;
import cn.itcast.shop.domain.Orders;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.service.CategoryService;
import cn.itcast.shop.service.OrderService;
import cn.itcast.shop.service.ProductService;
import cn.itcast.shop.service.impl.CategoryServiceImpl;
import cn.itcast.shop.service.impl.OrderServiceImpl;
import cn.itcast.shop.service.impl.ProductServiceImpl;
import cn.itcast.shop.utils.CommonUtils;
import cn.itcast.shop.utils.PaymentUtil;

import com.alibaba.fastjson.JSON;

public class ProductServlet extends BaseServlet {

	private Logger log = LogManager.getLogger(ProductServlet.class);

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ProductService service = new ProductServiceImpl();
		Product product = new Product();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 创建文件上传核心对象
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			// 解析request获得文件项集合

			List<FileItem> parseRequest = fileUpload.parseRequest(request);
			for (FileItem item : parseRequest) {
				// 判断是否普通表单项目
				boolean formField = item.isFormField();
				if (formField) {
					// 封装实体类对象
					String fieldName = item.getFieldName();
					String fieldValue = item.getString("UTF-8");
					map.put(fieldName, fieldValue);
				} else {
					String fieldName = item.getName();
					String path = this.getServletContext()
							.getRealPath("upload");
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(path + "/"
							+ fieldName);
					IOUtils.copy(in, out);
					in.close();
					out.close();
					item.delete();
					map.put("pimage", "upload/" + fieldName);
				}
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}

		try {
			BeanUtils.populate(product, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		// 设置uid
		product.setPid(CommonUtils.getUUID());
		// 设置添加日期
		product.setPdate(new Date());
		product.setPflag(0L);
		if(service.add(product)>0){
			//重定向到list
		}
	}

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
		// 记录客户端保存的pids
		String pids = "";
		// 获取客户端cookie记录
		Cookie[] cookies = request.getCookies();
		// 读取cookie
		for (Cookie cookie : cookies) {
			if ("pids".equals(cookie.getName())) {
				pids = cookie.getValue();
			}
		}
		// 存储查询记录的商品
		List<Product> historyList = new ArrayList<Product>();
		// 循环已经读取的pids
		if (pids.trim().length() > 0) {
			String[] split = pids.split("-");
			for (String pid : split) {
				Product p = productService.findProductByPid(pid);
				historyList.add(p);
			}

		}

		// 设置作用域
		request.setAttribute("historyList", historyList);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);

		// 转发
		request.getRequestDispatcher("/product_list.jsp").forward(request,
				response);
	}

	// 根据pid查询商品详细信息
	public void productByPid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 接收传递的参数
		// 获得要查询商品的id
		String pid = request.getParameter("pid");
		// 创建service对象
		ProductService productService = new ProductServiceImpl();
		// 生成商品对象
		Product product = null;
		// 当传递的参数的值不为空
		if (pid.length() > 0)
			// 接收service层传递的数据
			product = productService.findProductByPid(pid);

		log.info("客户端传递的商品id" + pid);
		// 日志记录当前对象
		log.info(product.toString());
		String pids = "";
		// 获取本地存储的商品pid
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// 遍历cookie
			for (Cookie cookie : cookies) {
				// 当cookie等于保存的商品id
				if ("pids".equals(cookie.getName())) {
					// 获取已经存在的商品id
					pids = cookie.getValue();
					// 截取
					String[] split = pids.split("-");
					List<String> alist = Arrays.asList(split);
					// 队列保存商品pid
					LinkedList<String> list = new LinkedList<String>(alist);
					if (list.contains(pid)) {
						list.remove(pid);
						list.addFirst(pid);
					} else {
						list.addFirst(pid);
					}
					// 拼接以-分割pid
					// 3,2,1;
					StringBuffer sb = new StringBuffer();
					for (int i = 0, len = list.size(); i < len && i < 7; i++) {
						if (!list.get(i).equals("null")) {
							sb.append(list.get(i)).append("-");
						}

					}
					pids = sb.substring(0, sb.length() - 1);
				}

			}
		}
		log.info("商品id" + pids);
		// 创建cookie客户端保存最近浏览商品的pid
		Cookie cookie = new Cookie("pids", pids);
		// 设置cookie最大时效
		cookie.setMaxAge(60 * 60);
		// 写入cookie
		response.addCookie(cookie);
		if (product != null) {
			// 设置作用域
			request.setAttribute("product", product);
			// 转发
			request.getRequestDispatcher("/product_info.jsp").forward(request,
					response);
		} else {
			// 查询不到
			log.warn("当前查询对象为空,请查询数据库");
		}

	}

	// 删除购物车
	public void delProductCartByPid(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 获取商品id
		String pid = request.getParameter("pid");
		// 获取存在session中的购物车对象
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 当前购物车对象不为空
		if (cart != null) {
			// 取出购物项目
			Map<String, CartItem> cartItems = cart.getCartItems();
			// 设置新的总价
			cart.setTotal(cart.getTotal() - cartItems.get(pid).getSurTotal());

			// 移除
			cartItems.remove(pid);
			cart.setCartItems(cartItems);
		}
		request.getSession().setAttribute("cart", cart);
		log.info(cart.toString());
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	// 清空购物车
	public void clearProductCart(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.getSession().setAttribute("cart", null);
		response.sendRedirect(request.getContextPath() + "/defaul.jsp");
	}

	// 添加购物车
	public void addProductToCart(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		// 获取商品id
		String pid = request.getParameter("pid");
		// 获取购买数量
		String num = request.getParameter("buyNum");
		// 转换购买数量为整数类型
		int buyNum = Integer.parseInt(num);
		ProductService productService = new ProductServiceImpl();
		// 获取商品信息
		Product product = productService.findProductByPid(pid);
		// 计算小计
		double subTotal = product.getShop_price() * buyNum;
		// 购物项
		CartItem item = new CartItem();
		item.setBuyNum(buyNum);
		item.setSurTotal(subTotal);
		item.setProduct(product);

		// 获取session中的购物车信息
		Cart cart = (Cart) session.getAttribute("cart");
		// session中没有购物车信息
		if (cart == null) {
			cart = new Cart();

		}

		double newsubtotal = 0.0;
		// 获取购物车中的购物项
		Map<String, CartItem> cartItems = cart.getCartItems();
		// 如果当前购物车中包含该商品
		if (cartItems.containsKey(pid)) {
			// 获取当前购物项
			CartItem cartItem = cartItems.get(pid);
			int oldBuyNum = cartItem.getBuyNum();
			oldBuyNum += buyNum;
			cartItem.setBuyNum(oldBuyNum);
			// 修改小计
			// 原来该商品的小计
			double oldsubtotal = cartItem.getSurTotal();
			// 新买的商品的小计
			newsubtotal = buyNum * product.getShop_price();
			cartItem.setSurTotal(oldsubtotal + newsubtotal);
			cart.setCartItems(cartItems);
		} else {
			// 如果不包含当前购物项
			// 如果车中没有该商品
			cart.getCartItems().put(product.getPid(), item);
			newsubtotal = buyNum * product.getShop_price();

		}
		// 计算总计
		double total = cart.getTotal() + newsubtotal;
		cart.setTotal(total);
		// 将车再次访问session
		session.setAttribute("cart", cart);

		// 直接跳转到购物车页面
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	// 修改购物车
	public void updateProductCartByPid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 获取商品id
		String pid = request.getParameter("pid");
		// 获得修改的购买数量
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));

		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null) {
			cart = new Cart();
		}
		double newSubTotal = 0.0;
		double oldSubtotal = 0.0;
		Map<String, CartItem> cartItems = cart.getCartItems();
		if (cartItems.containsKey(pid)) {
			CartItem cartItem = cartItems.get(pid);
			// 得到旧的小计
			oldSubtotal = cartItem.getSurTotal();
			// 修改新的小计
			newSubTotal = buyNum * cartItem.getProduct().getShop_price();
			cartItem.setSurTotal(newSubTotal);
			// 修改新的数量
			cartItem.setBuyNum(buyNum);
			cart.setCartItems(cartItems);
		}
		cart.setTotal(cart.getTotal() - oldSubtotal + newSubTotal);
		session.setAttribute("cart", cart);

		String cartToJson = JSON.toJSONString(cart);
		log.info(cartToJson);
		response.getWriter().write(cartToJson);
		// 直接跳转到购物车页面
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
}