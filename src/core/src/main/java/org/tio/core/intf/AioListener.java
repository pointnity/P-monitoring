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
	 */
	Void  onAfterClose ( ChannelContext  channelContext ,  Throwable  throwable ,  String  remark ,  boolean  isRemove )  throws  Exception ;

	/**
	 * This method is triggered after the chain is built. Note: Building the chain is not necessarily successful. You need to pay attention to the parameter isConnected.
	 * @param channelContext
	 * @param isConnected Whether the connection is successful, true: indicates that the connection is successful, false: indicates that the connection failed.
	 * @param isReconnect Whether it is reconnect, true: indicates that this is reconnect, false: indicates that this is the first connection
	 * @throws Exception
	 * @author: tanyaowu
	 */
	Void  onAfterConnected ( ChannelContext  channelContext ,  boolean  isConnected ,  boolean  isReconnect )  throws  Exception ;

	/**
	 * This method is triggered after successful decoding
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @throws Exception
	 * @author: tanyaowu
	 */
	Void  onAfterReceived ( ChannelContext  channelContext ,  Packet  packet ,  int  packetSize )  throws  Exception ;

	/**
	 * This method is triggered after the message packet is sent.
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess true: sent successfully, false: failed to send
	 * @throws Exception
	 * @author tanyaowu
	 *
	 */
	Void  onAfterSent ( ChannelContext  channelContext ,  Packet  packet ,  boolean  isSentSuccess )  throws  Exception ;

	/**
	 * This method is triggered before the connection is closed.
	 *
	 * @param channelContext the channelcontext
	 * @param throwable the throwable may be empty
	 * @param remark the remark may be empty
	 * @param isRemove
	 * @author tanyaowu
	 */
	Void  onBeforeClose ( ChannelContext  channelContext ,  Throwable  throwable ,  String  remark ,  boolean  isRemove );
}
