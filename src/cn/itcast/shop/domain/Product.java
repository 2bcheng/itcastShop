package cn.itcast.shop.domain;

import java.io.Serializable;

public class Product  implements Serializable  {
	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((is_hot == null) ? 0 : is_hot.hashCode());
		result = prime * result
				+ ((market_price == null) ? 0 : market_price.hashCode());
		result = prime * result + ((pdate == null) ? 0 : pdate.hashCode());
		result = prime * result + ((pdesc == null) ? 0 : pdesc.hashCode());
		result = prime * result + ((pflag == null) ? 0 : pflag.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((pimage == null) ? 0 : pimage.hashCode());
		result = prime * result + ((pname == null) ? 0 : pname.hashCode());
		result = prime * result
				+ ((shop_price == null) ? 0 : shop_price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (is_hot == null) {
			if (other.is_hot != null)
				return false;
		} else if (!is_hot.equals(other.is_hot))
			return false;
		if (market_price == null) {
			if (other.market_price != null)
				return false;
		} else if (!market_price.equals(other.market_price))
			return false;
		if (pdate == null) {
			if (other.pdate != null)
				return false;
		} else if (!pdate.equals(other.pdate))
			return false;
		if (pdesc == null) {
			if (other.pdesc != null)
				return false;
		} else if (!pdesc.equals(other.pdesc))
			return false;
		if (pflag == null) {
			if (other.pflag != null)
				return false;
		} else if (!pflag.equals(other.pflag))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (pimage == null) {
			if (other.pimage != null)
				return false;
		} else if (!pimage.equals(other.pimage))
			return false;
		if (pname == null) {
			if (other.pname != null)
				return false;
		} else if (!pname.equals(other.pname))
			return false;
		if (shop_price == null) {
			if (other.shop_price != null)
				return false;
		} else if (!shop_price.equals(other.shop_price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", market_price="
				+ market_price + ", shop_price=" + shop_price + ", pimage="
				+ pimage + ", pdate=" + pdate + ", is_hot=" + is_hot
				+ ", pdesc=" + pdesc + ", pflag=" + pflag + ", cid=" + cid
				+ "]";
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
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
	private String cid;

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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
}
