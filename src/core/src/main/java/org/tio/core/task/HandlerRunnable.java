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
	 */
	Public  void  handler ( Packet  packet )  {
		// int ret = 0;

		GroupContext  groupContext  =  channelContext . getGroupContext ();
		Try  {

			Integer  synSeq  =  packet . getSynSeq ();
			If  ( synSeq  !=  null  &&  synSeq  >  0 )  {
				ChannelContextMapWithLock  syns  =  channelContext . getGroupContext (). getWaitingResps ();
				Packet  initPacket  =  syns . remove ( synSeq );
				If  ( initPacket  !=  null )  {
					Synchronized  ( initPacket )  {
						SYNs . PUT ( synSeq ,  Packet );
						initPacket . notify ();
					}
				}  else  {
					Log . error ( "[{}] synchronization message failed, synSeq is {}, but there is no corresponding key value in the synchronization collection" ,  synFailCount . incrementAndGet (),  synSeq );
				}
			}  else  {
				channelContext . traceClient ( ChannelAction . BEFORE_HANDLER ,  packet ,  null );
				groupContext . getAioHandler (). handler ( packet ,  channelContext );
				channelContext . traceClient ( ChannelAction . AFTER_HANDLER ,  packet ,  null );
			}
			// ret++;
		}  catch  ( Throwable  e )  {
			Log . error ( e . toString ( ),  e );
			// return ret;
		}  finally  {
			channelContext . getStat (). getHandledPackets (). incrementAndGet ();
			channelContext . getStat (). getHandledBytes (). addAndGet ( packet . getByteCount ());

			groupContext . getGroupStat (). getHandledPacket (). incrementAndGet ();
			groupContext . getGroupStat (). getHandledBytes (). addAndGet ( packet . getByteCount ());
			
// channelContext.getIpStat().getHandledPackets().incrementAndGet();
// channelContext.getIpStat().getHandledBytes().addAndGet(packet.getByteCount());
			
// GuavaCache[] caches = channelContext.getGroupContext().ips.getCaches();
// for (GuavaCache guavaCache : caches) {
// IpStat ipStat = (IpStat) guavaCache.get(channelContext.getClientNode().getIp());
// ipStat.getHandledPackets().incrementAndGet();
// ipStat.getHandledBytes().addAndGet(packet.getByteCount());
// }
			
			List < Long >  list  =  groupContext . ipStats . durationList ;
			For  ( Long  v  :  list )  {
