package cn.itcast.shop.domain;

import java.io.Serializable;

public class Orderitem implements  Serializable {
	private static final long serialVersionUID = 1L;
	private String itemid;//订单项id
	private int count;//总数
	private Double subtotal;//总金额
	private Product product;//商品
	private Orders orders;//所属订单
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "Orderitem [itemid=" + itemid + ", count=" + count
				+ ", subtotal=" + subtotal + ", product=" + product
				+ ", orders=" + orders + "]";
	}
	public Orderitem(String itemid, int count, Double subtotal,
			Product product, Orders orders) {
		super();
		this.itemid = itemid;
		this.count = count;
		this.subtotal = subtotal;
		this.product = product;
		this.orders = orders;
	}
	public Orderitem() {
		super();
	}


}

