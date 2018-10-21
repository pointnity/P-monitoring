package org.tio.core;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.tio.core.intf.ChannelTraceHandler;
import org.tio.core.intf.Packet;
import org.tio.utils.json.Json;

import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateTime;

/**
 * @author tanyaowu
 *  
 */
public class DefaultChannelTraceHandler implements ChannelTraceHandler {
	//	private static Logger log = LoggerFactory.getLogger(DefaultClientTraceHandler.class);

	private Logger clientTraceLog = LoggerFactory.getLogger("tio-client-trace-log");

	/**
	 *
	 * @author tanyaowu
	 */
	public DefaultChannelTraceHandler() {
	}

	/**
	 * @param channelContext
	 * @param channelAction
	 * @param packet
	 * @param extmsg
	 * @author tanyaowu
	 */
	@Override
	public void traceChannel(ChannelContext channelContext, ChannelAction channelAction, Packet packet, Map<String, Object> extmsg) {
		if (clientTraceLog.isInfoEnabled()) {
			Map<String, Object> map = new HashMap<>(10);
			map.put("time", DateTime.now().toString(DatePattern.NORM_DATETIME_MS_FORMAT));
			map.put("action", channelAction);
			map.put("c_id", channelContext.getId());
			map.put("c", channelContext.toString());
			MDC.put("tio_client", channelContext.getClientNodeTraceFilename());

			if (packet != null) {
				map.put("p_id", channelContext.getClientNode().getPort() + "_" + packet.getId()); //packet id
				map.put("p_respId", packet.getRespId());
				map.put("packet", packet.logstr());
			}

			if (extmsg != null) {
