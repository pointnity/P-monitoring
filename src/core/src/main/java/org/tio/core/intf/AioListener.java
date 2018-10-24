Package  org . tio . core . intf ;

Import  org.tio.core.ChannelContext ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  interface  AioListener  {
	/**
	 * This method is triggered before and after the connection is closed.
	 *
	 * @param channelContext the channelcontext
	 * @param throwable the throwable may be empty
	 * @param remark the remark may be empty
	 * @param isRemove Is it deleted?
	 * @throws Exception
	 * @author: tanyaowu
