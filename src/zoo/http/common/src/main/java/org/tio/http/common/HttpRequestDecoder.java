package org.http.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.core.ChannelContext;
import org.core.exception.AioDecodeException;
import org.core.exception.LengthOverflowException;
import org.core.utils.ByteBufferUtils;
import org.http.common.HttpConst.RequestBodyFormat;
import org.http.common.utils.HttpParseUtils;

import com.xiaoleilu.hutool.util.StrUtil;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpRequestDecoder {
	public static enum Step {
		firstline, header, body
	}

	private static Logger log = LoggerFactory.getLogger(HttpRequestDecoder.class);

	/**
	 * The maximum number of bytes in a head
	 */
	public static final int MAX_HEADER_LENGTH = 20480;

	/**
	 * Head, maximum number of bytes per line
	 */
	public static final int MAX_LENGTH_OF_LINE = 2048;

	public static HttpRequest decode(ByteBuffer buffer, ChannelContext channelContext) throws AioDecodeException {
		int initPosition = buffer.position();
		//		int count = 0;
		Step step = Step.firstline;
		//		StringBuilder currLine = new StringBuilder();
		Map<String, String> headers = new HashMap<>();
		int contentLength = 0;
		byte[] bodyBytes = null;
		StringBuilder headerSb = new StringBuilder(512);
		RequestLine firstLine = null;

		while (buffer.hasRemaining()) {
			String line;
			try {
				line = ByteBufferUtils.readLine(buffer, null, MAX_LENGTH_OF_LINE);
			} catch (LengthOverflowException e) {
				throw new AioDecodeException(e);
			}

			int newPosition = buffer.position();
			if (newPosition - initPosition > MAX_HEADER_LENGTH) {
				throw new AioDecodeException("max http header length " + MAX_HEADER_LENGTH);
			}

			if (line == null) {
				return null;
			}

			headerSb.append(line).append("\r\n");
			if ("".equals(line)) {//The head resolution is complete.
				String contentLengthStr = headers.get(HttpConst.RequestHeaderKey.Content_Length);
				if (StringUtils.isBlank(contentLengthStr)) {
					contentLength = 0;
				} else {
					contentLength = Integer.parseInt(contentLengthStr);
				}

				int readableLength = buffer.limit() - buffer.position();
				if (readableLength >= contentLength) {
					step = Step.body;
					break;
				} else {
					return null;
				}
			} else {
				if (step == Step.firstline) {
					firstLine = parseRequestLine(line, channelContext);
					step = Step.header;
				} else if (step == Step.header) {
					KeyValue keyValue = parseHeaderLine(line);
					headers.put(keyValue.getKey(), keyValue.getValue());
				}
				continue;
			}
		}

		if (step != Step.body) {
			return null;
		}

		if (!headers.containsKey(HttpConst.RequestHeaderKey.Host)) {
			throw new AioDecodeException("there is no host header");
		}

		HttpRequest httpRequest = new HttpRequest(channelContext.getClientNode());
		httpRequest.setChannelContext(channelContext);
		httpRequest.setHttpConfig((HttpConfig) channelContext.getGroupContext().getAttribute(GroupContextKey.HTTP_SERVER_CONFIG));
		httpRequest.setHeaderString(headerSb.toString());
		httpRequest.setRequestLine(firstLine);
		httpRequest.setHeaders(headers);
		httpRequest.setContentLength(contentLength);

		if (contentLength == 0) {
			if (StringUtils.isNotBlank(firstLine.getQuery())) {
				Map<String, Object[]> params = decodeParams(firstLine.getQuery(), httpRequest.getCharset(), channelContext);
				httpRequest.setParams(params);
			}
		} else {
			bodyBytes = new byte[contentLength];
			buffer.get(bodyBytes);
			httpRequest.setBody(bodyBytes);
			//Parsing the message body
			parseBody(httpRequest, firstLine, bodyBytes, channelContext);
		}

		//Analytical User_Agent(Browser operating system and other information)
		//		String User_Agent = headers.get(HttpConst.RequestHeaderKey.User_Agent);
		//		if (StringUtils.isNotBlank(User_Agent)) {
		//			//			long start = System.currentTimeMillis();
		//			UserAgentAnalyzer userAgentAnalyzer = UserAgentAnalyzerFactory.getUserAgentAnalyzer();
		//			UserAgent userAgent = userAgentAnalyzer.parse(User_Agent);
		//			httpRequest.setUserAgent(userAgent);
		//		}
