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
	private String line;

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * /user/get
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	public String getPathAndQuery() {
		return pathAndQuery;
	}

	/**
	 * name=tan&id=789
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * /user/get
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public void setPathAndQuery(String pathAndQuery) {
		this.pathAndQuery = pathAndQuery;
	}

	/**
