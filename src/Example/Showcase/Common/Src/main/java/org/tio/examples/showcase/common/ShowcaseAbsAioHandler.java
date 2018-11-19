Package  org . tio . examples . showcase . common ;

Import  java.nio.ByteBuffer ;

Import  org.tio.core.ChannelContext ;
Import  org.tio.core.GroupContext ;
Import  org.tio.core.exception.AioDecodeException ;
Import  org.tio.core.intf.AioHandler ;
Import  org.tio.core.intf.Packet ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  abstract  class  ShowcaseAbsAioHandler  implements  AioHandler  {
	/**
	 * Decoding: Decode the received ByteBuffer into a service message packet that the application can recognize.
	 * Header: type + bodyLength
	 * Message body: byte[]
	 */
	@Override
	Public  ShowcasePacket  decode ( ByteBuffer  buffer ,  ChannelContext  channelContext )  throws  AioDecodeException  {
		Int  readableLength  =  buffer . limit ()  -  buffer . position ();
		If  ( readableLength  <  ShowcasePacket . HEADER_LENGHT )  {
			Return  null ;
		}

		//Message type
