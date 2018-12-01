package org.tio.examples.showcase.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.examples.showcase.common.ShowcasePacket;
import org.tio.examples.showcase.common.ShowcaseSessionContext;
import org.tio.examples.showcase.common.Type;
import org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler;
import org.tio.examples.showcase.common.packets.P2PReqBody;
import org.tio.examples.showcase.common.packets.P2PRespBody;
import org.tio.utils.json.Json;

/**
 * @author tanyaowu
 *  
 */
public class P2PReqHandler extends AbsShowcaseBsHandler<P2PReqBody> {
	private static Logger log = LoggerFactory.getLogger(P2PReqHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	public P2PReqHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<P2PReqBody> bodyClass() {
		return P2PReqBody.class;
	}

	/**
	 * @param packet
	 * @param bsBody
