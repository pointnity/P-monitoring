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
