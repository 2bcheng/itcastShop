package cn.itcast.shop.service;

import java.sql.SQLException;

import cn.itcast.shop.domain.Orders;

public interface OrderService {

	public  void submitOrder(Orders orders);

	public void updateOrders(Orders orders);

	public void updateStateByOid(String r6_Order) ;

}
