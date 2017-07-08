package cn.itcast.shop.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;
	private String oid;// 订单id
	private Date ordertime;// 订单时间
	private Double total;// 订单金额
	private int state;// 订单状态1付款,0未付款
	private String address;// 订单地址
	private String name;// 收件人
	private String telephone;// 收件人联系电话
	public List<Orderitem> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

	private User user;// 所属用户

	// 所属订单项目
	private List<Orderitem> orderitems = new ArrayList<Orderitem>();

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}



	@Override
	public String toString() {
		return "Orders [oid=" + oid + ", ordertime=" + ordertime + ", total="
				+ total + ", state=" + state + ", address=" + address
				+ ", name=" + name + ", telephone=" + telephone + ", user="
				+ user + ", orderitems=" + orderitems + "]";
	}

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Orders(String oid, Date ordertime, Double total, int state,
			String address, String name, String telephone, User user) {
		super();
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.name = name;
		this.telephone = telephone;
		this.user = user;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

}
