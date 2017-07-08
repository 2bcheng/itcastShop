package cn.itcast.shop.service;

import java.util.List;
import java.util.Map;

import cn.itcast.shop.domain.Orders;
import cn.itcast.shop.domain.PageBean;

public interface OrderService {

	public void submitOrder(Orders orders);

	public void updateOrders(Orders orders);

	public void updateStateByOid(String r6_Order);

	public PageBean<Orders> findOrdersByUid(String uid, int currentPage,
			int currentCount);

	public List<Map<String, Object>> findOrderItemsByOid(String oid);

	public List<Orders> findOrdersByUid(String uid);

}
