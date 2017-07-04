package cn.itcast.shop.domain;

import java.io.Serializable;

public class Orderitem implements  Serializable {
	private static final long serialVersionUID = 1L;
	private String itemid;
	private int count;
	private Double subtotal;
	private String pid;
	private String oid;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getCount() {
		return count;
	}


	@Override
	public String toString() {
		return "Orderitem [itemid=" + itemid + ", count=" + count
				+ ", subtotal=" + subtotal + ", pid=" + pid + ", oid=" + oid
				+ "]";
	}

	public Orderitem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Orderitem(String itemid, int count, Double subtotal, String pid,
			String oid) {
		super();
		this.itemid = itemid;
		this.count = count;
		this.subtotal = subtotal;
		this.pid = pid;
		this.oid = oid;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((itemid == null) ? 0 : itemid.hashCode());
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result
				+ ((subtotal == null) ? 0 : subtotal.hashCode());
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
		Orderitem other = (Orderitem) obj;
		if (count != other.count)
			return false;
		if (itemid == null) {
			if (other.itemid != null)
				return false;
		} else if (!itemid.equals(other.itemid))
			return false;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		if (subtotal == null) {
			if (other.subtotal != null)
				return false;
		} else if (!subtotal.equals(other.subtotal))
			return false;
		return true;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
}
