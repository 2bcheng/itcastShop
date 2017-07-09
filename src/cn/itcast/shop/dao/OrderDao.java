package cn.itcast.shop.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.itcast.shop.domain.Orders;

public interface OrderDao {

	public void addOrderItems(Orders order) throws SQLException;

	public void addOrders(Orders order) throws SQLException;

	public void updateOrders(Orders orders) throws SQLException;

	public void updateStateByOid(String oid) throws SQLException;

	public List<Orders> findOrdersByUid(String uid, int index, int currentCount)
			throws SQLException;

	public List<Orders> findOrdersByUid(String uid) throws SQLException;

	public List<Map<String, Object>> findOrderItemsByOid(String oid)
			throws SQLException;

	public int getAll(String uid) throws SQLException;

	public List<Orders> getAll() throws SQLException;

}
