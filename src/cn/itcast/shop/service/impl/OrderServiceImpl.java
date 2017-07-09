package cn.itcast.shop.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.itcast.shop.dao.OrderDao;
import cn.itcast.shop.dao.impl.OrderDaoImpl;
import cn.itcast.shop.domain.Orders;
import cn.itcast.shop.domain.PageBean;
import cn.itcast.shop.service.OrderService;
import cn.itcast.shop.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void submitOrder(Orders order) {
		OrderDao orderDao = new OrderDaoImpl();
		try {
			DataSourceUtils.startTransaction();
			orderDao.addOrders(order);
			orderDao.addOrderItems(order);
			DataSourceUtils.commit();
		} catch (SQLException e) {
			try {
				DataSourceUtils.rollBack();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DataSourceUtils.closeConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateOrders(Orders orders) {
		OrderDao dao = new OrderDaoImpl();
		try {
			dao.updateOrders(orders);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateStateByOid(String r6_Order) {
		OrderDao dao = new OrderDaoImpl();
		try {
			dao.updateStateByOid(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Orders> findOrdersByUid(String uid) {

		OrderDao dao = new OrderDaoImpl();
		List<Orders> orders = null;
		try {
			orders = dao.findOrdersByUid(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public List<Map<String, Object>> findOrderItemsByOid(String oid) {
		OrderDao dao = new OrderDaoImpl();
		List<Map<String, Object>> orders = null;
		try {
			orders = dao.findOrderItemsByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public PageBean<Orders> findOrdersByUid(String uid, int currentPage,
			int currentCount) {
		OrderDao dao = new OrderDaoImpl();
		PageBean<Orders> pageBean = new PageBean<Orders>();
		List<Orders> orders = null;

		// 获取所有订单的总数
		int count = 0;
		try {
			count = dao.getAll(uid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 获取当前页面总数
		if (currentCount == 0)
			currentCount = 6;
		// 获取当前页面
		if (currentPage == 0)
			currentPage = 1;
		// 当前页数据
		int index = (currentPage - 1) * currentCount;

		// 总页数
		int total = (int) Math.ceil(1.0 * count / currentCount);

		pageBean.setCurrentCount(currentCount);
		pageBean.setCurrentPage(currentPage);
		pageBean.setTotalCount(count);
		pageBean.setTotalPage(total);

		try {
			orders = dao.findOrdersByUid(uid, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setList(orders);
		return pageBean;
	}

	public List<Orders> getAllOrders() {
		try {
			return new OrderDaoImpl().getAll();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		}
	}

}
