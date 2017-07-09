package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.itcast.shop.domain.Cart;
import cn.itcast.shop.domain.CartItem;
import cn.itcast.shop.domain.Orderitem;
import cn.itcast.shop.domain.Orders;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.domain.Product;
import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.OrderService;
import cn.itcast.shop.service.impl.OrderServiceImpl;
import cn.itcast.shop.utils.CommonUtils;
import cn.itcast.shop.utils.PaymentUtil;

public class OrderServlet extends BaseServlet {
	private Logger log = LogManager.getLogger(OrderServlet.class);

	public void myOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// 判断用户是否登录
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String strCurrentPage = request.getParameter("currentPage");
		// 当前页数
		int currentPage = 1;
		if (strCurrentPage.length() > 0) {
			currentPage = Integer.parseInt(strCurrentPage);
		}
		// 当前页面显示条数
		int currentCount = 6;

		OrderService service = new OrderServiceImpl();

		// 获取订单
		List<Orders> orderList = new ArrayList<Orders>();
		// 获取当前用户所有的订单

		PageBean<Orders> pageBean = service.findOrdersByUid(user.getUid(),
				currentPage, currentCount);
		orderList = service.findOrdersByUid(user.getUid());

		log.info("当前servlet:myOrders,用来查询用户所属的订单" + orderList);
		// 订单不为空
		if (orderList != null) {
			// 循环迭代订单根据订单号获取所有订单项
			for (Orders order : orderList) {
				// 获取数据库中,属于当前订单的所有订单项目
				List<Map<String, Object>> orderItems = service
						.findOrderItemsByOid(order.getOid());
				log.info("当前servlet:myOrders,用来查询用户所属的订单的订单项目" + orderItems);
				for (Map<String, Object> map : orderItems) {
					try {
						// 从map中读取数据封装到orderItem对象中
						Orderitem orderitem = new Orderitem();
						BeanUtils.populate(orderitem, map);
						log.info("当前servlet:myOrders,用来封装用户所属订单的订单项目"
								+ orderItems);
						// 从map中读取数据封装到product对象中
						Product p = new Product();
						BeanUtils.populate(p, map);
						log.info("当前servlet:myOrders,用来查询用户所属的订单的订单商品" + p);
						// 添加商品到购物项目
						orderitem.setProduct(p);
						// 添加购物项到订单
						order.getOrderitems().add(orderitem);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
//		// 分页订单不为空
//		if (pageBean != null) {
//			// 循环迭代订单根据订单号获取所有订单项
//			for (Orders order : pageBean.getList()) {
//				// 获取数据库中,属于当前订单的所有订单项目
//				List<Map<String, Object>> orderItems = service
//						.findOrderItemsByOid(order.getOid());
//				log.info("当前servlet:myOrders,用来查询用户所属的订单的订单项目" + orderItems);
//				for (Map<String, Object> map : orderItems) {
//					try {
//						// 从map中读取数据封装到orderItem对象中
//						Orderitem orderitem = new Orderitem();
//						BeanUtils.populate(orderitem, map);
//						log.info("当前servlet:myOrders,用来封装用户所属订单的订单项目"
//								+ orderItems);
//						// 从map中读取数据封装到product对象中
//						Product p = new Product();
//						BeanUtils.populate(p, map);
//						log.info("当前servlet:myOrders,用来查询用户所属的订单的订单商品" + p);
//						// 添加商品到购物项目
//						orderitem.setProduct(p);
//						// 添加购物项到订单
//						order.getOrderitems().add(orderitem);
//					} catch (IllegalAccessException e) {
//						e.printStackTrace();
//					} catch (InvocationTargetException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
		log.info("展示的数据" + orderList);
		request.setAttribute("pageBean", pageBean);
		log.info("分页bean的数据" + pageBean);
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request,
				response);
	}

	// 更新收件人收件地址,以及联系电话

	public void updateOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 获取收件人,地址电话等信息
		Map<String, String[]> properties = request.getParameterMap();
		Orders orders = new Orders();
		try {
			BeanUtils.populate(orders, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		OrderService orderService = new OrderServiceImpl();
		orderService.updateOrders(orders);

		String orderid = request.getParameter("oid");
		// 获取银行信息
		String pd_FrpId = request.getParameter("pd_FrpId");
		// String money = order.getTotal()+"";//支付金额
		String money = "0.01";// 支付金额

		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString(
				"p1_MerId");
		String p2_Order = orderid;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString(
				"callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="
				+ pd_FrpId + "&p0_Cmd=" + p0_Cmd + "&p1_MerId=" + p1_MerId
				+ "&p2_Order=" + p2_Order + "&p3_Amt=" + p3_Amt + "&p4_Cur="
				+ p4_Cur + "&p5_Pid=" + p5_Pid + "&p6_Pcat=" + p6_Pcat
				+ "&p7_Pdesc=" + p7_Pdesc + "&p8_Url=" + p8_Url + "&p9_SAF="
				+ p9_SAF + "&pa_MP=" + pa_MP + "&pr_NeedResponse="
				+ pr_NeedResponse + "&hmac=" + hmac;

		response.sendRedirect(url);

	}

	public void submitOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		Orders orders = new Orders();
		// 获取购物车
		Cart cart = (Cart) session.getAttribute("cart");
		// 设置订单所属者
		orders.setUser(user);
		// 订单地址
		orders.setAddress(null);
		// 订单状态
		orders.setState(0);
		// 订单id
		orders.setOid(CommonUtils.getUUID());
		// 设置收件人
		orders.setName(null);
		// 订单总金额
		orders.setTotal(cart.getTotal());
		// 订单创建时间
		orders.setOrdertime(new Date());

		if (cart != null) {
			// 获取购物项目
			Map<String, CartItem> cartItems = cart.getCartItems();
			for (Entry<String, CartItem> entry : cartItems.entrySet()) {
				CartItem cartItem = entry.getValue();
				Orderitem orderitem = new Orderitem();
				// 设置订单项的购买数量
				orderitem.setCount(cartItem.getBuyNum());
				// 订单项id
				orderitem.setItemid(CommonUtils.getUUID());
				// 设置订单项里的商品
				orderitem.setProduct(cartItem.getProduct());
				// 所属订单
				orderitem.setOrders(orders);
				// 订单项总金额
				orderitem.setSubtotal(cartItem.getSurTotal());
				orders.getOrderitems().add(orderitem);
			}
		}

		OrderService orderService = new OrderServiceImpl();
		orderService.submitOrder(orders);
		session.setAttribute("orders", orders);
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");
	}
}
