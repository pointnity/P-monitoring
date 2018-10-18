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
