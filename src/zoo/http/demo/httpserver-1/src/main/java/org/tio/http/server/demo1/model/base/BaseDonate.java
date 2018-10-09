package org.tio.http.server.demo1.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseDonate<M extends BaseDonate<M>> extends Model<M> implements IBean {

	private static final long serialVersionUID = -7992873894635460955L;

	public java.lang.Double getAmount() {
		return get("amount");
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public java.lang.String getLeavemsg() {
		return get("leavemsg");
	}

	public java.lang.String getMyremark() {
		return get("myremark");
	}

	public java.lang.String getName() {
		return get("name");
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public java.util.Date getTime() {
		return get("time");
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public java.lang.String getWay() {
		return get("way");
	}

	public void setAmount(java.lang.Double amount) {
		set("amount", amount);
	}

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public void setLeavemsg(java.lang.String leavemsg) {
		set("leavemsg", leavemsg);
	}

	public void setMyremark(java.lang.String myremark) {
		set("myremark", myremark);
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public void setTime(java.util.Date time) {
		set("time", time);
	}
