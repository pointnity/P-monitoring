package org.tio.client;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.SystemTimer;
import org.tio.utils.thread.pool.DefaultThreadFactory;

/**
 *
 * @author tanyaowu
 *  
 */
public class ReconnConf {
	private static Logger log = LoggerFactory.getLogger(ChannelContext.class);

	public static boolean isNeedReconn(ClientChannelContext clientChannelContext, boolean putIfTrue) {
		ClientGroupContext clientGroupContext = (ClientGroupContext) clientChannelContext.getGroupContext();
		ReconnConf reconnConf = clientGroupContext.getReconnConf();
		if (reconnConf != null && reconnConf.getInterval() > 0) {
			if (reconnConf.getRetryCount() <= 0 || reconnConf.getRetryCount() >= clientChannelContext.getReconnCount()) {
				if (putIfTrue) {
					clientChannelContext.getStat().setTimeInReconnQueue(SystemTimer.currentTimeMillis());
					reconnConf.getQueue().add(clientChannelContext);
				}
