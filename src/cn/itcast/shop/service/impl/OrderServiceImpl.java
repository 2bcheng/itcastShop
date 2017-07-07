package cn.itcast.shop.service.impl;

import java.sql.SQLException;

import cn.itcast.shop.dao.OrderDao;
import cn.itcast.shop.dao.impl.OrderDaoImpl;
import cn.itcast.shop.domain.Orders;
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

}
