package org.tio.http.server;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpConst;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.server.intf.ServerAioListener;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpServerAioListener implements ServerAioListener {

	//	private static Logger log = LoggerFactory.getLogger(HttpServerAioListener.class);
	private static Logger iplog = LoggerFactory.getLogger("tio-ip-trace-log");

	static Map<String, AtomicLong> ipmap = new java.util.concurrent.ConcurrentHashMap<>();
	static AtomicLong accessCount = new AtomicLong();

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	public static void main(String[] args) {
	}

	/**
	 *
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public HttpServerAioListener() {
	}

	/**
	 * @see org.tio.core.intf.AioListener#onAfterClose(org.tio.core.ChannelContext, java.lang.Throwable, java.lang.String)
	 *
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @author tanyaowu
	 *  
	 *
	 */
