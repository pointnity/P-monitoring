package org.tio.http.server.demo1.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

 /**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	private static final long serialVersionUID = -5967132586596290127L;

	public java.lang.String getAvatar() {
		return get("avatar");
	}

	public java.util.Date getCreatetime() {
		return get("createtime");
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public java.lang.String getIp() {
