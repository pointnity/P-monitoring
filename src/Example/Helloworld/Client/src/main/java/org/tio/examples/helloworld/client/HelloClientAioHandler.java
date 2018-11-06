Package  org . tio . examples . helloworld . client ;

Import  java.nio.ByteBuffer ;

Import  org.tio.client.intf.ClientAioHandler ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.core.GroupContext ;
Import  org.tio.core.exception.AioDecodeException ;
Import  org.tio.core.intf.Packet ;
Import  org.tio.examples.helloworld.common.HelloPacket ;

/**
 * 
 * @author tanyaowu
 */
Public  class  HelloClientAioHandler  implements  ClientAioHandler  {
	Private  static  HelloPacket  heartbeatPacket  =  new  HelloPacket ();


	/**
	 * Decoding: Decode the received ByteBuffer into a service message packet that the application can recognize.
	 * Total message structure: message header + message body
	 * Message header structure: 4 bytes, storing the length of the message body
	 * Message body structure: the byte[] of the object's json string
	 */
	@Override
	Public  HelloPacket  decode ( ByteBuffer  buffer ,  ChannelContext  channelContext )  throws  AioDecodeException  {
		Int  readableLength  =  buffer . limit ()  -  buffer . position ();
		/ / Received data group can not be a business package, then return null to tell the framework data is not enough
		If  ( readableLength  <  HelloPacket . HEADER_LENGHT )  {
			Return  null ;
		}

		/ / Read the length of the message body
		Int  bodyLength  =  buffer . getInt ();

		/ / The data is incorrect, throw an AioDecodeException
		If  ( bodyLength  <  0 )  {
			Throw  new  AioDecodeException ( "bodyLength ["  +  bodyLength  +  "] is not right, remote:"  +  channelContext . getClientNode ());
		}

		/ / Calculate the length of data required this time
		Int  neededLength  =  HelloPacket . HEADER_LENGHT  +  bodyLength ;
		/ / Is the data received enough for the package?
		Int  isDataEnough  =  readableLength  -  neededLength ;
