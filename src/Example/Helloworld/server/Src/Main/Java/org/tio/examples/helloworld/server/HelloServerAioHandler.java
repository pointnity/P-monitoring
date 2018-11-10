Package  org . tio . examples . helloworld . server ;

Import  java.nio.ByteBuffer ;

Import  org.tio.core.Aio ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.core.GroupContext ;
Import  org.tio.core.exception.AioDecodeException ;
Import  org.tio.core.intf.Packet ;
Import  org.tio.examples.helloworld.common.HelloPacket ;
Import  org.tio.server.intf.ServerAioHandler ;

/**
 * @author tanyaowu
 */
Public  class  HelloServerAioHandler  implements  ServerAioHandler  {

	/**
	 * Decoding: Decode the received ByteBuffer into a service message packet that the application can recognize.
	 * Total message structure: message header + message body
	 * Message header structure: 4 bytes, storing the length of the message body
	 * Message body structure: the byte[] of the object's json string
	 */
