package cn.itcast.shop.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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

	@Override
	public List<Orders> findOrdersByUid(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select  *  from  orders  where  uid=? ";

		return qr.query(sql, new BeanListHandler<Orders>(Orders.class), uid);
	}
	public List<Orders> findOrdersByUid(String uid,int index,int  currentCount) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select  *  from  orders  where  uid=?  limit ?,?";

		return qr.query(sql, new BeanListHandler<Orders>(Orders.class), uid,index,currentCount);
	}

	@Override
	public List<Map<String, Object>> findOrderItemsByOid(String oid)
			throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select  count,subtotal,pimage,pname,shop_price  from  orderitem o,product p  where  o.pid=p.pid  and  o.oid=?";
		return qr.query(sql, new MapListHandler(), oid);
	}

	@Override
	public int getAll(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select  count(*)  from  orders  where uid=?";
		Long  count=(Long) qr.query(sql, new  ScalarHandler(),uid);
		return count.intValue();
	}

	public List<Orders> getAll() throws SQLException {
		QueryRunner queryRunner=new QueryRunner(DataSourceUtils.getDataSource());
		String  sql="select  * from  orders";
		
		return   queryRunner.query(sql, new  BeanListHandler<Orders>(Orders.class));
	}
}
