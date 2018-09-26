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
