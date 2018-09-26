package org.http.common;

/**
 * gitee.generallycloud/baseio<br>
 *
 *
 * @author tanyaowu
 *
 */
public enum HttpResponseStatus {

	/**
	 *The initial request has been accepted and the customer should continue to send the remainder of the request.(HTTP 1.1 new)
	 */
	C100(100, "Continue", "100 Continue"),
	/**
	 *The server translates the client's request to another protocol (HTTP 1.1 new)
	 */
	C101(101, "Switching Protocols", "101 Switching Protocols"),
	/**
	 *Everything is OK, the answer document for Get and post requests is followed.
	 */
	C200(200, "OK", "200 OK"),
	/**
	 *The server has created the document, and the location header gives its URL.
	 */
	C201(201, "Created", "201 Created"),
	/**
	 *The request has been accepted, but the processing has not been completed.
	 */
	C202(202, "Accepted", "202 Accepted"),
	/**
