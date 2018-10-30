/**
 *
 */
Package  org . tio . core . task ;

Import  java.util.List ;
Import  java.util.concurrent.Executor ;
Import  java.util.concurrent.atomic.AtomicLong ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelAction ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.core.GroupContext ;
Import  org.tio.core.intf.Packet ;
Import  org.tio.core.maintain.ChannelContextMapWithLock ;
Import  org.tio.core.stat.IpStat ;
Import  org.tio.utils.thread.pool.AbstractQueueRunnable ;

/**
 *
 * @author 
 * 
 *
 */
Public  class  HandlerRunnable  extends  AbstractQueueRunnable < Packet >  {
	Private  static  Final  Logger  log  =  of LoggerFactory . the getLogger ( HandlerRunnable . class );

	Private  ChannelContext  channelContext  =  null ;

	Private  AtomicLong  synFailCount  =  new  AtomicLong ();

	Public  HandlerRunnable ( ChannelContext  channelContext ,  Executor  executor )  {
		Super ( executor );
		the this . channelContext  =  channelContext ;
	}

	/**
	 * Processing packets
	 * @param packet
	 * @return
	 *
	 * @author tanyaowu
