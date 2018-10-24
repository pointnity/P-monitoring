Package  org . tio . core . intf ;

Import  java.util.Map ;

Import  org.tio.core.ChannelAction ;
Import  org.tio.core.ChannelContext ;

/**
 * @author tanyaowu
 *  
 */
Public  interface  ChannelTraceHandler  {
	/**
	 *
	 * @param channelContext
	 * @param channelAction
	 * @param packet
	 * @param extmsg
	 * @author tanyaowu
	 */
	Public  void  traceChannel ( ChannelContext  channelContext ,  ChannelAction  channelAction ,  Packet  packet ,  Map < String ,  Object >  extmsg );
}
