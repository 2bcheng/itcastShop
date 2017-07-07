package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.itcast.shop.domain.Cart;
import cn.itcast.shop.domain.CartItem;
import cn.itcast.shop.domain.Orderitem;
import cn.itcast.shop.domain.Orders;
import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.OrderService;
import cn.itcast.shop.service.impl.OrderServiceImpl;
import cn.itcast.shop.utils.CommonUtils;

public class OrderServlet extends BaseServlet {
	private Logger log = LogManager.getLogger(OrderServlet.class);

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
