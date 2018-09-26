package org.http.common;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;

/**
 *
 * @author tanyaowu
 * 
 */
public class HttpResponseEncoder {
	public static enum Step {
		firstline, header, body
	}

	private static Logger log = LoggerFactory.getLogger(HttpResponseEncoder.class);

	public static final int MAX_HEADER_LENGTH = 20480;

	/**
	 *
	 * @param httpResponse
	 * @param groupContext
	 * @param channelContext
	 * @param skipCookie true: Ignore the encoding of the cookie section
	 * @return
	 * @author tanyaowu
	 */
	public static ByteBuffer encode(HttpResponse httpResponse, GroupContext groupContext, ChannelContext channelContext, boolean skipCookie) {
		byte[] encodedBytes = httpResponse.getEncodedBytes();
		if (encodedBytes != null) {
			ByteBuffer ret = ByteBuffer.wrap(encodedBytes);
			ret.position(ret.limit());
			return ret;
		}

		int bodyLength = 0;
		byte[] body = httpResponse.getBody();
		if (body != null) {
			bodyLength = body.length;
		}

		StringBuilder sb = new StringBuilder(256);

		HttpResponseStatus httpResponseStatus = httpResponse.getStatus();
		//		httpResponseStatus.get
		sb.append("HTTP/1.1 ").append(httpResponseStatus.getStatus()).append(" ").append(httpResponseStatus.getDescription()).append("\r\n");

		Map<String, String> headers = httpResponse.getHeaders();
		if (headers != null && headers.size() > 0) {
			headers.put(HttpConst.ResponseHeaderKey.Content_Length, bodyLength + "");
			Set<Entry<String, String>> set = headers.entrySet();
			for (Entry<String, String> entry : set) {
				sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
			}
		}

		if (!skipCookie) {
			//Processing cookies
			List<Cookie> cookies = httpResponse.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					sb.append(HttpConst.ResponseHeaderKey.Set_Cookie).append(": ");
					sb.append(cookie.toString());
					sb.append("\r\n");
					if (log.isInfoEnabled()) {
						log.info("{}, set-cookie:{}", channelContext, cookie.toString());
					}
				}
			}
		}

		sb.append("\r\n");

		byte[] headerBytes = null;
		try {
			String headerString = sb.toString();
			httpResponse.setHeaderString(headerString);
			headerBytes = headerString.getBytes(httpResponse.getCharset());
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
