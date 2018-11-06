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
		// Not enough message body length (the remaining buffe group can't be a message body)
		If  ( isDataEnough  <  0 )  {
			Return  null ;
		}  else  //The package is successful
		{
			HelloPacket  imPacket  =  new  HelloPacket ();
			If  ( bodyLength  >  0 )  {
				Byte []  dst  =  new  byte [ bodyLength ];
				Buffer . get ( dst );
				imPacket . setBody ( dst );
			}
			Return  imPacket ;
		}
	}

	/**
	 * Encoding: encode the service message packet into a ByteBuffer that can be sent.
	 * Total message structure: message header + message body
	 * Message header structure: 4 bytes, storing the length of the message body
	 * Message body structure: the byte[] of the object's json string
	 */
	@Override
	Public  ByteBuffer  encode ( Packet  packet ,  GroupContext  groupContext ,  ChannelContext  channelContext )  {
		HelloPacket  helloPacket  =  ( HelloPacket )  packet ;
		byte []  body  =  helloPacket . the getBody ();
		Int  bodyLen  =  0 ;
		If  ( body  !=  null )  {
			bodyLen  =  body . length ;
		}

		//The total length of the bytebuffer is = the length of the message header + the length of the message body
		Int  allLen  =  HelloPacket . HEADER_LENGHT  +  bodyLen ;
		/ / Create a new bytebuffer
		ByteBuffer  buffer  =  ByteBuffer . allocate ( allLen );
		/ / Set the endian
		Buffer . Order ( groupContext . getByteOrder ());

		//Write the message header----The content of the message header is the length of the message body.
		Buffer . putInt ( bodyLen );

		/ / Write message body
		If  ( body  !=  null )  {
			Buffer . put ( body );
		}
		Return  buffer ;
	}
	
	/**
	 * Processing messages
	 */
	@Override
	Public  void  handler ( Packet  packet ,  ChannelContext  channelContext )  throws  Exception  {
		HelloPacket  helloPacket  =  ( HelloPacket )  packet ;
		byte []  body  =  helloPacket . the getBody ();
		If  ( body  !=  null )  {
			String  str  =  new  String ( body ,  HelloPacket . CHARSET );
			System . out . println ( "received message:"  +  str );
		}

		Return ;
	}

	/**
	 * If this method returns null, the framework layer will not send a heartbeat; if it returns non-null, the framework layer will periodically send the message packet returned by this method.
	 */
	@Override
	Public  HelloPacket  heartbeatPacket ()  {
		Return  heartbeatPacket ;
	}
}
