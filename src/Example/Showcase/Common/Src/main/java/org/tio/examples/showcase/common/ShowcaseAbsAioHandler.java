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
