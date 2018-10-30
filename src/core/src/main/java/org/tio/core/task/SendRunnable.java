Package  org . tio . core . task ;

Import  java.nio.ByteBuffer ;
Import  java.nio.channels.AsynchronousSocketChannel ;
Import  java.util.ArrayList ;
Import  java.util.List ;
Import  java.util.concurrent.Executor ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelAction ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.core.GroupContext ;
Import  org.tio.core.WriteCompletionHandler ;
Import  org.tio.core.WriteCompletionHandler.WriteCompletionVo ;
Import  org.tio.core.intf.AioHandler ;
Import  org.tio.core.intf.Packet ;
Import  org.tio.core.intf.PacketWithMeta ;
Import  org.tio.core.utils.AioUtils ;
Import  org.tio.utils.thread.pool.AbstractQueueRunnable ;

/**
 *
 * @author tanyaowu
*  
 */
Public  class  SendRunnable  extends  AbstractQueueRunnable < Object >  {

	Private  static  final  Logger  log  =  LoggerFactory . getLogger ( SendRunnable . class );

	Private  ChannelContext  channelContext  =  null ;

	/**
	 *
	 * @param channelContext
	 * @param executor
	 * @author tanyaowu
	 */
	Public  SendRunnable ( ChannelContext  channelContext ,  Executor  executor )  {
		Super ( executor );
		the this . channelContext  =  channelContext ;
	}

	/**
	 *
	 */
	@Override
	Public  boolean  addMsg ( Object  obj )  {
		If  ( this . isCanceled ())  {
			Log . error ( "{}, the task has been canceled, {} added to the send queue failed" ,  channelContext ,  obj );
			Return  false ;
		}

		Return  msgQueue . add ( obj );
	}
