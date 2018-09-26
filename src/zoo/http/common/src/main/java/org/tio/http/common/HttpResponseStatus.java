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
	 *There is no new content, but the browser should reset what it displays.Used to force the browser to clear the form input (HTTP 1.1 new).
	 */
	C205(205, "Reset Content", "205 Reset Content"),
	/**
	 *There is no new content, but the browser should reset what it displays.Used to force the browser to clear the form input (HTTP 1.1 new).
	 */
	C206(206, "Partial Content", "206 Partial Content"),
	/**
	 *The documents requested by the customer can be found in multiple locations that are listed in the returned document.If the server wants to make a preference, it should be indicated in the location answer header.
	 */
	C300(300, "Multiple Choices", "300 Multiple Choices"),
	/**
	 *The document requested by the customer elsewhere, the new URL is given in the location header, and the browser should automatically access the new URL.
	 */
	C301(301, "Moved Permanently", "301 Moved Permanently"),
	/**
	 *Similar to 301, but the new URL should be treated as a temporary replacement instead of permanent.Note that the corresponding status information in HTTP1.0 is "Moved temporatily".When the status code appears, the browser can automatically access the new URL, so it is a useful status code.Note that this status code can sometimes be used with 301 substitutions.For example, if the browser mistakenly requests Http://host/~user (the trailing slash is missing), some servers return 301, and some return 302.Strictly speaking, we can only assume that the browser will automatically redirect only if the original request is get.See 307.
	 */
	C302(302, "Found", "302 Found"),
	/**
	 *Similar to 301/302, the difference is that if the original request is the Post,location header the specified redirect target document should be fetched via get (HTTP 1.1 new).
	 */
	C303(303, "See Other", "303 See Other"),
	/**
	 *The client has a buffered document and issues a conditional request (typically providing a if-modified-since header indicating that the customer only wants to update the document than the specified date).The server tells the customer that the original buffered document can continue to be used.
	 */
	C304(304, "Not Modified", "304 Not Modified"),
	/**
	 *The document requested by the client should be extracted from the proxy server indicated by the location header (HTTP 1.1 is new).
	 */
	C305(305, "Use Proxy", "305 Use Proxy"),
	/**
	 *Same as 302 (Found).Many browsers incorrectly respond to a 302 response for redirection, even if the original request is post, even though it can actually be redirected only if the answer to the POST request is 303.For this reason, HTTP 1.1 has been added 307 to allow for more cleanup of the region in several status codes: When a 303 response occurs, the browser can follow the redirected get and post requests;If 307 is the answer, the browser can only follow the redirect to the GET request.(HTTP 1.1 new)
	 */
	C307(307, "Temporary Redirect", "307 Temporary Redirect"),
	/**
