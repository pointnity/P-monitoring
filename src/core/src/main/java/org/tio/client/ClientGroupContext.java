package org.tio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.GroupContext;
import org.tio.core.intf.AioHandler;
import org.tio.core.intf.AioListener;
import org.tio.core.stat.GroupStat;

/**
 *
 * @author tanyaowu
 *  
 */
public class ClientGroupContext extends GroupContext {
	static Logger log = LoggerFactory.getLogger(ClientGroupContext.class);

	private ClientAioHandler clientAioHandler = null;

	private ClientAioListener clientAioListener = null;

	private ClientGroupStat clientGroupStat = new ClientGroupStat();

	private ConnectionCompletionHandler connectionCompletionHandler = new ConnectionCompletionHandler();

	/**
	 *Do not re-connect
	 * @param aioHandler
	 * @param aioListener
	 * @author tanyaowu
	 */
	public ClientGroupContext(ClientAioHandler aioHandler, ClientAioListener aioListener) {
		this(aioHandler, aioListener, null);
	}

	/**
	 *
	 * @param aioHandler
	 * @param aioListener
	 * @param reconnConf Null is not required to automatically re-connect
	 * @author tanyaowu
	 */
	public ClientGroupContext(ClientAioHandler aioHandler, ClientAioListener aioListener, ReconnConf reconnConf) {
		super();

		this.setClientAioHandler(aioHandler);
		this.setClientAioListener(aioListener);

		this.reconnConf = reconnConf;
	}

	/**
	 * @see org.tio.core.GroupContext#getAioHandler()
	 *
	 * @return
	 * @author tanyaowu
	 *  
	 *
	 */
	@Override
	public AioHandler getAioHandler() {
		return this.getClientAioHandler();
	}

	/**
	 * @see org.tio.core.GroupContext#getAioListener()
	 *
	 * @return
	 * @author tanyaowu
	 *  
	 *
	 */
