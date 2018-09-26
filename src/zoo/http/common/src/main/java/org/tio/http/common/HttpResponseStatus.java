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
	 *The document has returned normally, but some of the answer headers may be incorrect because a copy of the document is being used (HTTP 1.1 is new).
	 */
	C203(203, "Non-Authoritative Information", "203 Non-Authoritative Information"),
	/**
	 *Without a new document, the browser should continue to display the original document.This status code is useful if the user refreshes the page on a regular basis and the servlet can determine that the user's document is new enough.
	 */
	C204(204, "No Content", "204 No Content"),
	/**
