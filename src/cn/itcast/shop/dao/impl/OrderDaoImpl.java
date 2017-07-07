package cn.itcast.shop.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.shop.dao.OrderDao;
import cn.itcast.shop.domain.Orderitem;
import cn.itcast.shop.domain.Orders;
import cn.itcast.shop.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void addOrderItems(Orders order) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = DataSourceUtils.getConnection();
		String sql = "insert into  orderitem  values(?,?,?,?,?)";

		List<Orderitem> orderitems = order.getOrderitems();
		for (Orderitem orderitem : orderitems) {
			queryRunner.update(conn, sql, orderitem.getItemid(), orderitem
					.getCount(), orderitem.getSubtotal(), orderitem
					.getProduct().getPid(), orderitem.getOrders().getOid());
		}

	}

	@Override
	public void addOrders(Orders order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		Connection conn = DataSourceUtils.getConnection();
		String sql = "insert into orders  values(?,?,?,?,?,?,?,?)";
		qr.update(conn, sql, order.getOid(), order.getOrdertime(),
				order.getTotal(), order.getState(), order.getAddress(),
				order.getName(), order.getTelephone(), order.getUser().getUid());
	}

	@Override
	public void updateOrders(Orders orders) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(
				DataSourceUtils.getDataSource());
		String sql = "update  orders  set address=?,name=?,telephone=? where oid=?";
		queryRunner.update(sql, orders.getAddress(), orders.getName(),
				orders.getTelephone(), orders.getOid());

	}

	@Override
	public void updateStateByOid(String oid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(
				DataSourceUtils.getDataSource());
		String sql = "update  orders  set state= 1  where oid=?";
		queryRunner.update(sql, oid);
	}
}
