package org.http.common;

import java.util.Objects;

/**
 * @author tanyaowu
 *   
 */
public enum Method {
	GET("GET"), POST("POST"), HEAD("HEAD"), PUT("PUT"), TRACE("TRACE"), OPTIONS("OPTIONS"), PATCH("PATCH");
	public static Method from(String method) {
		Method[] values = Method.values();
		for (Method v : values) {
			if (Objects.equals(v.value, method)) {
				return v;
			}
		}
		return GET;
	}

	String value;

	private Method(String value) {
		this.value = value;
	}
}
