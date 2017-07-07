package cn.itcast.shop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
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
import cn.itcast.shop.domain.User;
import cn.itcast.shop.service.OrderService;
import cn.itcast.shop.service.impl.OrderServiceImpl;
import cn.itcast.shop.utils.CommonUtils;
import cn.itcast.shop.utils.PaymentUtil;

public class OrderServlet extends BaseServlet {
	private Logger log = LogManager.getLogger(OrderServlet.class);

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
