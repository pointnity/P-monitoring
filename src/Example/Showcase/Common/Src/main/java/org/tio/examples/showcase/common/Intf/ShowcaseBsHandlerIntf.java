Package  org . tio . examples . showcase . common . intf ;

Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;

/**
 * Business processor interface
 * @author tanyaowu
 *  
 */
Public  interface  ShowcaseBsHandlerIntf  {

	/**
	 *
	 * @param packet
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	Public  Object  handler ( ShowcasePacket  packet ,  ChannelContext  channelContext )  throws  Exception ;

}
