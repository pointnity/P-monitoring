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
		byte  type  =  Buffer . GET ();

		int  BodyLength  =  Buffer . the getInt ();

		IF  ( BodyLength  <  0 )  {
			Throw  new  AioDecodeException ( "bodyLength ["  +  bodyLength  +  "] is not right, remote:"  +  channelContext . getClientNode ());
		}

		Int  neededLength  =  ShowcasePacket . HEADER_LENGHT  +  bodyLength ;
		int  Test  =  readableLength  -  neededLength ;
		If  ( test  <  0 )  // not enough message body length (the remaining buffe group can not be the message body)
		{
			Return  null ;
		}  else  {
			ShowcasePacket  imPacket  =  new  ShowcasePacket ();
			imPacket . setType ( of the type );
			If  ( bodyLength  >  0 )  {
				Byte []  dst  =  new  byte [ bodyLength ];
				Buffer . get ( dst );
