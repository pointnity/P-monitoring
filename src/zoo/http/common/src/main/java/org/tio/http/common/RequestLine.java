package org.http.common;

/**
 * @author tanyaowu
 *  
 */
public class RequestLine {
	private Method method;
	private String path; //user/get
	private String query; //tan&id=789
	private String pathAndQuery;
	private String protocol;
	private String version;
