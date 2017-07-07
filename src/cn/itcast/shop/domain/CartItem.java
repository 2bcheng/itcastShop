package cn.itcast.shop.domain;

import java.io.Serializable;

public class CartItem implements Serializable{
	private static final long serialVersionUID = 1L;
	private Product product;
	private int buyNum;
	private double surTotal;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public double getSurTotal() {
		return surTotal;
	}

	public void setSurTotal(double surTotal) {
		this.surTotal = surTotal;
	}

}
