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
	 *A syntax error occurred in the request.
	 */
	C400(400, "Bad Request", "400 Bad Request"),
	/**
	 *The customer attempted to access a password-protected page without authorization.A www-authenticate header is included in the answer, and the browser displays the user name/Password dialog box, and then makes a request again after filling in the appropriate authorization header.
	 */
	C401(401, "Unauthorized", "401 Unauthorized"),
	/**
	 *The resource is not available.The server understands the customer's request, but refuses to process it.This is usually caused by the permissions set on the file or directory on the server.
	 */
	C403(403, "Forbidden", "403 Forbidden"),
	/**
	 *The resource at the specified location could not be found.This is also a common answer.
	 */
	C404(404, "Not Found", "404 Not Found"),
	/**
	 *The request method (GET, POST, HEAD, DELETE, PUT, Trace, and so on) does not apply to the specified resource.(HTTP 1.1 new)
	 */
	C405(405, "Method Not Allowed", "405 Method Not Allowed"),
	/**
	 *The specified resource has been found, but its MIME type is incompatible with the client specified in the Accpet header (HTTP 1.1 new).
	 */
	C406(406, "Not Acceptable", "406 Not Acceptable"),
	/**
	 *Similar to 401, indicates that the customer must be authorized by the proxy server first.(HTTP 1.1 new)
	 */
	C407(407, "Proxy Authentication Required", "407 Proxy Authentication Required"),
	/**
	 *The customer has not made any requests during the waiting time for the server license.Customers can repeat the same request at a later time.(HTTP 1.1 new)
	 */
	C408(408, "Request Timeout", "408 Request Timeout"),
	/**
	 *Usually related to put requests.The request cannot succeed because the request conflicts with the current state of the resource.(HTTP 1.1 new)
	 */
	C409(409, "Conflict", "409 Conflict"),
	/**
	 *The requested document is no longer available, and the server does not know which address to redirect to.It differs from 404 in that returning 407 means that the document has permanently left the specified location, and 404 indicates that the document is unavailable for unknown reasons.(HTTP 1.1 new)
	 */
	C410(410, "Gone", "410 Gone"),
	/**
	 *The server cannot process the request unless the customer sends a content-length header.(HTTP 1.1 new)
	 */
	C411(411, "Length Required", "411 Length Required"),
	/**
	 *Some of the prerequisites specified in the request header failed (HTTP 1.1 new).
	 */
	C412(412, "Precondition Failed", "412 Precondition Failed"),
	/**
	 *The size of the destination document exceeds the size that the server is currently willing to handle.If the server thinks it can process the request later, it should provide a Retry-after header (HTTP 1.1 new).
	 */
	C413(413, "Request Entity Too Large", "413 Request Entity Too Large"),
	/**
	 *The URI is too long (HTTP 1.1 new).
	 */
	C414(414, "Request URI Too Long", "414 Request URI Too Long"),
	/**
	 *The server does not meet the range header specified by the customer in the request.(HTTP 1.1 new)
	 */
	C416(416, "Requested Range Not Satisfiable", "416 Requested Range Not Satisfiable"),
	/**
	 *The server encountered an unexpected situation and could not complete the customer's request.
	 */
	C500(500, "Internal Server Error", "500 Internal Server Error"),
	/**
	 *The server does not support the functionality required to implement the request.For example, a customer sends a put request that is not supported by the server.
	 */
	C501(501, "Not Implemented", "501 Not Implemented"),
	/**
	 *When the server acts as a gateway or proxy, the server returns an illegal response in order to complete the request to access the next server.
	 */
	C502(502, "Bad Gateway", "502 Bad Gateway"),
	/**
	 *The server failed to answer due to maintenance or heavy load.For example, a servlet might return 503 if the database connection pool is full.A retry-after header can be supplied when the server returns 503.
	 */
	C503(503, "Service Unavailable", "503 Service Unavailable"),
	/**
	 *Used by a server acting as a proxy or gateway, indicating that an answer cannot be received from a remote server in a timely manner.(HTTP 1.1 new)
	 */
	C504(504, "Gateway Timeout", "504 Gateway Timeout"),
	/**
