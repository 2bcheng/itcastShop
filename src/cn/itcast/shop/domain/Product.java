package cn.itcast.shop.domain;

import java.io.Serializable;
import java.sql.Date;

public class Product  implements Serializable  {
	private static final long serialVersionUID = 1L;

	public Product() {
		super();
	}

	public Product(String pid, String pname, Double market_price,
			Double shop_price, String pimage, Date pdate, Long is_hot,
			String pdesc, Long pflag, Category category) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.market_price = market_price;
		this.shop_price = shop_price;
		this.pimage = pimage;
		this.pdate = pdate;
		this.is_hot = is_hot;
		this.pdesc = pdesc;
		this.pflag = pflag;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", market_price="
				+ market_price + ", shop_price=" + shop_price + ", pimage="
				+ pimage + ", pdate=" + pdate + ", is_hot=" + is_hot
				+ ", pdesc=" + pdesc + ", pflag=" + pflag + ", category="
				+ category + "]";
	}

	private String pid;
	private String pname;
	private Double market_price;
	private Double shop_price;
	private String pimage;
	private java.sql.Date pdate;
	private Long is_hot;
	private String pdesc;
	private Long pflag;
	private Category category;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Double market_price) {
		this.market_price = market_price;
	}

	public Double getShop_price() {
		return shop_price;
	}

	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public java.sql.Date getPdate() {
		return pdate;
	}

	public void setPdate(java.sql.Date pdate) {
		this.pdate = pdate;
	}

	public Long getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(Long is_hot) {
		this.is_hot = is_hot;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public Long getPflag() {
		return pflag;
	}

	public void setPflag(Long pflag) {
		this.pflag = pflag;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
