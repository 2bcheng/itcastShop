package cn.itcast.shop.dao;

import java.sql.SQLException;

import cn.itcast.shop.domain.Orders;

public interface OrderDao {

	public void addOrderItems(Orders order) throws SQLException;

	public void addOrders(Orders order) throws SQLException;

	public void updateOrders(Orders orders) throws SQLException;

	public  void  updateStateByOid(String oid)  throws  SQLException;

}
