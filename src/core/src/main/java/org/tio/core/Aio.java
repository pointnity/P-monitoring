package org.tio.core;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.intf.Packet;
import org.tio.core.intf.PacketWithMeta;
import org.tio.core.maintain.ChannelContextMapWithLock;
import org.tio.core.task.SendRunnable;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.ObjWithLock;
import org.tio.utils.lock.SetWithLock;
import org.tio.utils.page.Page;
import org.tio.utils.page.PageUtils;
import org.tio.utils.thread.ThreadUtils;

/**
 * The Class Aio. T-io users care about the API almost all in this
 *
 * @author tanyaowu
 */
public class Aio {
	private Aio() {}


	/** The log. */
	private static Logger log = LoggerFactory.getLogger(Aio.class);

	/**
	 * Bind groups
	 * @param channelContext
	 * @param group
	 * @author tanyaowu
	 */
	public static void bindGroup(ChannelContext channelContext, String group) {
		channelContext.getGroupContext().groups.bind(group, channelContext);
	}
	
	/**
	 * Whether a channel is in a group
	 * @param channelContext
	 * @param group
	 * @return true：In this group
	 * @author: tanyaowu
	 */
	public static boolean isInGroup(ChannelContext channelContext, String group) {
		MapWithLock<ChannelContext, SetWithLock<String>> mapWithLock = channelContext.getGroupContext().groups.getChannelmap();
		
		ReadLock lock = mapWithLock.getLock().readLock();
		try {
			lock.lock();
			Map<ChannelContext, SetWithLock<String>> m = mapWithLock.getObj();
			if (m == null || m.size() == 0) {
				return false;
			}
			SetWithLock<String> set = m.get(channelContext);
			if (set == null) {
				return false;
			}
			return set.getObj().contains(group);
		} catch (Throwable e) {
			log.error(e.toString(), e);
			return false;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * How many connections are there in a group
	 * @param group
	 * @return
	 * @author tanyaowu
	 */
	public static int groupCount(GroupContext groupContext, String group) {
		SetWithLock<ChannelContext> setWithLock = groupContext.groups.clients(groupContext, group);
		if (setWithLock == null) {
			return 0;
		}
		
		return setWithLock.getObj().size();
	}

	/**
	 *Bind user
	 * @param channelContext
	 * @param userid
	 * @author tanyaowu
	 */
	public static void bindUser(ChannelContext channelContext, String userid) {
		channelContext.getGroupContext().users.bind(userid, channelContext);
	}
	
	/**
	 * Binding token
	 * @param channelContext
	 * @param token
	 * @author tanyaowu
	 */
	public static void bindToken(ChannelContext channelContext, String token) {
		channelContext.getGroupContext().tokens.bind(token, channelContext);
	}

	/**
	 * Blocking the sending of messages to the specified Channelcontext
	 * @param channelContext
	 * @param packet
	 * @return
	 * @author tanyaowu
	 */
	public static Boolean bSend(ChannelContext channelContext, Packet packet) {
		if (channelContext == null) {
			return false;
		}
		CountDownLatch countDownLatch = new CountDownLatch(1);
		return send(channelContext, packet, countDownLatch, PacketSendMode.SINGLE_BLOCK);
	}

	/**
	 * Sent to the specified IP and port
	 * @param groupContext
	 * @param ip
	 * @param port
	 * @param packet
	 * @author tanyaowu
	 */
	public static Boolean bSend(GroupContext groupContext, String ip, int port, Packet packet) {
		return send(groupContext, ip, port, packet, true);
	}

	/**
	 * Send messages to all connections
	 * @param groupContext
	 * @param packet
	 * @param channelContextFilter
	 * @author tanyaowu
	 */
	public static Boolean bSendToAll(GroupContext groupContext, Packet packet, ChannelContextFilter channelContextFilter) {
		return sendToAll(groupContext, packet, channelContextFilter, true);
	}

	/**
	 *Send Message to Group
	 * @param groupContext
	 * @param group
	 * @param packet
	 * @author tanyaowu
	 */
	public static void bSendToGroup(GroupContext groupContext, String group, Packet packet) {
		bSendToGroup(groupContext, group, packet, null);
	}

	/**
	 * Send Message to Group
	 * @param groupContext
	 * @param group
	 * @param packet
	 * @param channelContextFilter
	 * @author tanyaowu
	 */
	public static Boolean bSendToGroup(GroupContext groupContext, String group, Packet packet, ChannelContextFilter channelContextFilter) {
		return sendToGroup(groupContext, group, packet, channelContextFilter, true);
	}

	/**
	 * Sends a message to the specified Channelcontext ID
	 * @param channelId
	 * @param packet
	 * @author tanyaowu
	 */
	public static void bSendToId(GroupContext groupContext, String channelId, Packet packet) {
		sendToId(groupContext, channelId, packet, true);
	}

	/**
	 * Sends a message to the specified collection
	 * @param groupContext
	 * @param setWithLock
	 * @param packet
	 * @param channelContextFilter
	 * @author tanyaowu
	 */
	public static Boolean bSendToSet(GroupContext groupContext, ObjWithLock<Set<ChannelContext>> setWithLock, Packet packet, ChannelContextFilter channelContextFilter) {
		return sendToSet(groupContext, setWithLock, packet, channelContextFilter, true);
	}

	/**
	 * Block messages to specified users
	 * @param groupContext
	 * @param userid
	 * @param packet
	 * @return
	 * @author tanyaowu
	 */
	Public  static  Boolean  bSendToUser ( GroupContext  groupContext ,  String  userid ,  Packet  packet )  {
		Return  sendToUser ( groupContext ,  userid ,  packet ,  true );
	}

	/**
	 * Block sending a message to the specified token
	 * @param groupContext
	 * @param token
	 * @param packet
	 * @return
	 * @author tanyaowu
	 */
	Public  static  Boolean  bSendToToken ( GroupContext  groupContext ,  String  token ,  Packet  packet )  {
		Return  sendToToken ( groupContext ,  token ,  packet ,  true );
	}
	/**
	 * Close the connection
	 * @param channelContext
	 * @param remark
	 * @author tanyaowu
	 */
	Public  static  void  close ( ChannelContext  channelContext ,  String  remark )  {
		Close ( channelContext ,  null ,  remark );
	}

	/**
	 * Close the connection
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @author tanyaowu
	 */
	Public  static  void  close ( ChannelContext  channelContext ,  Throwable  throwable ,  String  remark )  {
		Close ( channelContext ,  throwable ,  remark ,  false );
	}

	/**
	 *
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isNeedRemove
	 * @author tanyaowu
	 */
	Private  static  void  close ( ChannelContext  channelContext ,  Throwable  throwable ,  String  remark ,  boolean  isNeedRemove )  {
		If  ( channelContext . isWaitingClose ())  {
			Log . debug ( "{} is waiting to be closed" ,  channelContext );
			Return ;
		}

		Synchronized  ( channelContext )  {
			//double check
			If  ( channelContext . isWaitingClose ())  {
				Log . debug ( "{} is waiting to be closed" ,  channelContext );
				Return ;
			}
			channelContext . setWaitingClose ( true );
			ThreadPoolExecutor  closePoolExecutor  =  channelContext . getGroupContext (). getTioExecutor ();
			closePoolExecutor . execute ( new  CloseRunnable ( channelContext ,  throwable ,  remark ,  isNeedRemove ));
		}
	}

	/**
	 * Close the connection
	 * @param groupContext
	 * @param clientIp
	 * @param clientPort
	 * @param throwable
	 * @param remark
	 * @author tanyaowu
	 */
	Public  static  void  close ( GroupContext  groupContext ,  String  clientIp ,  Integer  clientPort ,  Throwable  throwable ,  String  remark )  {
		ChannelContext  channelContext  =  groupContext . clientNodeMap . find ( clientIp ,  clientPort );
		Close ( channelContext ,  throwable ,  remark );
	}

	/**
	 * Get all connections, including those currently disconnected
	 * @param groupContext
	 * @return
	 * @author tanyaowu
	 */
	Public  static  SetWithLock < ChannelContext >  getAllChannelContexts ( GroupContext  groupContext )  {
		Return  groupContext . connections . getSetWithLock ();
	}

	/**
	 * Get all connections that are in a normal connection
	 * @param groupContext
	 * @return
	 * @author tanyaowu
	 */
	Public  static  SetWithLock < ChannelContext >  getAllConnectedsChannelContexts ( GroupContext  groupContext )  {
		Return  groupContext . connecteds . getSetWithLock ();
	}

	/**
	 * Get ChannelContext based on clientip and clientport
	 * @param groupContext
	 * @param clientIp
	 * @param clientPort
	 * @return
	 * @author tanyaowu
	 */
	Public  static  ChannelContext  getChannelContextByClientNode ( GroupContext  groupContext ,  String  clientIp ,  Integer  clientPort )  {
		Return  groupContext . clientNodeMap . find ( clientIp ,  clientPort );
	}

	/**
	 * Get ChannelContext based on id
	 * @param channelId
	 * @return
	 * @author tanyaowu
	 */
	Public  static  ChannelContext  getChannelContextById ( GroupContext  groupContext ,  String  channelId )  {
		Return  groupContext . ids . find ( groupContext ,  channelId );
	}

	/**
	 * Get SetWithLock<ChannelContext> based on userid
	 * @param groupContext
	 * @param userid
	 * @return
	 * @author tanyaowu
	 */
	Public  static  SetWithLock < ChannelContext >  getChannelContextsByUserid ( GroupContext  groupContext ,  String  userid )  {
		Return  groupContext . users . find ( groupContext ,  userid );
	}
	
	/**
	 * Get SetWithLock<ChannelContext> based on the token
	 * @param groupContext
	 * @param token
	 * @return
	 * @author tanyaowu
	 */
	Public  static  SetWithLock < ChannelContext >  getChannelContextsByToken ( GroupContext  groupContext ,  String  token )  {
		Return  groupContext . tokens . find ( groupContext ,  token );
	}

	/**
	 * Get all clients of a group
	 * @param groupContext
	 * @param group
	 * @return
	 * @author tanyaowu
	 */
	Public  static  SetWithLock < ChannelContext >  getChannelContextsByGroup ( GroupContext  groupContext ,  String  group )  {
		Return  groupContext . groups . clients ( groupContext ,  group );
	}

	/**
	 *
	 * @param groupContext
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @author tanyaowu
	 */
	Public  static  Page < ChannelContext >  getPageOfAll ( GroupContext  groupContext ,  Integer  pageIndex ,  Integer  pageSize )  {
		SetWithLock < ChannelContext >  setWithLock  =  Aio . getAllChannelContexts ( groupContext );
		Return  PageUtils . fromSetWithLock ( setWithLock ,  pageIndex ,  pageSize );
	}

	/**
	 * This method is for the server side.
	 * @param groupContext
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @author tanyaowu
	 */
	Public  static  Page < ChannelContext >  getPageOfConnecteds ( GroupContext  groupContext ,  Integer  pageIndex ,  Integer  pageSize )  {
		ObjWithLock < Set < ChannelContext >>  objWithLock  =  Aio . getAllConnectedsChannelContexts ( groupContext );
		Return  PageUtils . fromSetWithLock ( objWithLock ,  pageIndex ,  pageSize );
	}

	/**
	 *
	 * @param groupContext
	 * @param group
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @author tanyaowu
	 */
	Public  static  Page < ChannelContext >  getPageOfGroup ( GroupContext  groupContext ,  String  group ,  Integer  pageIndex ,  Integer  pageSize )  {
		ObjWithLock < Set < ChannelContext >>  objWithLock  =  Aio . getChannelContextsByGroup ( groupContext ,  group );
		Return  PageUtils . fromSetWithLock ( objWithLock ,  pageIndex ,  pageSize );
	}

	/**
	 * Same as the close method, except that no maintenance operations such as reconnection are performed.
	 * @param channelContext
	 * @param remark
	 * @author tanyaowu
	 */
	Public  static  void  remove ( ChannelContext  channelContext ,  String  remark )  {
		Remove ( channelContext ,  null ,  remark );
	}

	/**
	 * Remove all connections from client ip to the specified value
	 * @param groupContext
	 * @param ip
	 * @param remark
	 * @author: tanyaowu
	 */
	Public  static  void  remove ( GroupContext  groupContext ,  String  ip ,  String  remark )  {
		SetWithLock < ChannelContext >  setWithLock  =  Aio . getAllChannelContexts ( groupContext );
		Lock  lock2  =  setWithLock . getLock (). readLock ();
		Try  {
			Lock2 . lock ();
			Set < ChannelContext >  set  =  setWithLock . getObj ();
			For  ( ChannelContext  channelContext  :  set )  {
				String  clientIp  =  channelContext . getClientNode (). getIp ();
				If  ( StringUtils . equals ( clientIp ,  ip ))  {
					Aio . remove ( channelContext ,  remark );
				}
			}
		}  finally  {
			Lock2 . unlock ();
		}
	}

	/**
	 * Same as the close method, except that no maintenance operations such as reconnection are performed.
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @author tanyaowu
	 */
	Public  static  void  remove ( ChannelContext  channelContext ,  Throwable  throwable ,  String  remark )  {
		Close ( channelContext ,  throwable ,  remark ,  true );
	}

	/**
	 * Same as the close method, except that no maintenance operations such as reconnection are performed.
	 * @param groupContext
	 * @param clientIp
	 * @param clientPort
	 * @param throwable
	 * @param remark
	 * @author tanyaowu
	 */
	Public  static  void  remove ( GroupContext  groupContext ,  String  clientIp ,  Integer  clientPort ,  Throwable  throwable ,  String  remark )  {
		ChannelContext  channelContext  =  groupContext . clientNodeMap . find ( clientIp ,  clientPort );
		Remove ( channelContext ,  throwable ,  remark );
	}

	/**
	 * Send a message to the specified ChannelContext
	 * @param channelContext
	 * @param packet
	 * @author tanyaowu
	 */
	Public  static  Boolean  send ( ChannelContext  channelContext ,  Packet  packet )  {
		Return  send ( channelContext ,  packet ,  null ,  null );
	}

	/**
	 *
	 * @param channelContext
	 * @param packet
	 * @param countDownLatch
	 * @param packetSendMode
	 * @return
	 * @author tanyaowu
	 */
	Private  static  Boolean  send ( final  ChannelContext  channelContext ,  final  Packet  packet ,  CountDownLatch  countDownLatch ,  PacketSendMode  packetSendMode )  {
		Try  {
			If  ( packet  ==  null )  {
				Return  false ;
			}

			If  ( channelContext  ==  null  ||  channelContext . isClosed ()  ||  channelContext . isRemoved ())  {
				If  ( countDownLatch  !=  null )  {
					countDownLatch . countDown ();
				}
				If  ( channelContext  !=  null )  {
					Log . error ( "{}, isClosed:{}, isRemoved:{}, stack:{} " ,  channelContext ,  channelContext . isClosed (),  channelContext . isRemoved (),  ThreadUtils . stackTrace ());
				}
				Return  false ;
			}

			Boolean  isSingleBlock  =  countDownLatch  !=  null  &&  packetSendMode  ==  PacketSendMode . SINGLE_BLOCK ;

			SendRunnable  sendRunnable  =  channelContext . getSendRunnable ();
			PacketWithMeta  packetWithMeta  =  null ;
			Boolean  isAdded  =  false ;
			If  ( countDownLatch  ==  null )  {
				isAdded  =  sendRunnable . addMsg ( packet );
			}  else  {
				packetWithMeta  =  new  PacketWithMeta ( packet ,  countDownLatch );
				isAdded  =  sendRunnable . addMsg ( packetWithMeta );
			}

			If  (! isAdded )  {
				If  ( countDownLatch  !=  null )  {
					countDownLatch . countDown ();
				}
				Return  false ;
			}

			//SynThreadPoolExecutor synThreadPoolExecutor = channelContext.getGroupContext().getGroupExecutor();
			channelContext . getGroupContext (). getTioExecutor (). execute ( sendRunnable );

			If  ( isSingleBlock )  {
				Long  timeout  =  10 ;
				Try  {
					channelContext . traceBlockPacket ( SynPacketAction . BEFORE_WAIT ,  packet ,  countDownLatch ,  null );
					Boolean  awaitFlag  =  countDownLatch . await ( timeout ,  TimeUnit . SECONDS );
					channelContext . traceBlockPacket ( SynPacketAction . AFTER__WAIT ,  packet ,  countDownLatch ,  null );
					//log.error("{} after await, packet:{}, countDownLatch:{}", channelContext, packet.logstr(), countDownLatch);

					If  (! awaitFlag )  {
						Log . error ( "{} blocking send timeout, timeout:{}s, packet:{}" ,  channelContext ,  timeout ,  packet . logstr ());
					}
				}  catch  ( InterruptedException  e )  {
					Log . error ( e . toString ( ),  e );
				}

				Boolean  isSentSuccess  =  packetWithMeta . getIsSentSuccess ();
				Return  isSentSuccess ;
			}  else  {
				Return  true ;
			}
		}  catch  ( Throwable  e )  {
			Log . error ( e . toString ( ),  e );
			Return  false ;
		}  finally  {
			// if (isSingleBlock)
			// {
			// org.tio.core.GroupContext.SYN_SEND_SEMAPHORE.release();
			// }
		}

	}

	/**
	 * sent to the specified ip and port
	 * @param groupContext
	 * @param ip
	 * @param port
	 * @param packet
	 * @author tanyaowu
	 */
	Public  static  Boolean  send ( GroupContext  groupContext ,  String  ip ,  int  port ,  Packet  packet )  {
		Return  send ( groupContext ,  ip ,  port ,  packet ,  false );
	}

	/**
	 * sent to the specified ip and port
	 * @param groupContext
	 * @param ip
	 * @param port
	 * @param packet
	 * @param isBlock
	 * @return
	 * @author tanyaowu
	 */
	Private  static  Boolean  send ( GroupContext  groupContext ,  String  ip ,  int  port ,  Packet  packet ,  boolean  isBlock )  {
		ChannelContext  channelContext  =  groupContext . clientNodeMap . find ( ip ,  port );
		If  ( channelContext  !=  null )  {
			If  ( isBlock )  {
				Return  bSend ( channelContext ,  packet );
			}  else  {
				Return  send ( channelContext ,  packet );
			}
		}  else  {
			Log . info ( "{}, can find channelContext by {}:{}" ,  groupContext . getName (),  ip ,  port );
			Return  false ;
		}
	}

	Public  static  void  sendToAll ( GroupContext  groupContext ,  Packet  packet )  {
		sendToAll ( groupContext ,  packet ,  null );
	}

	/**
	 * Send a message to all connections
	 * @param groupContext
	 * @param packet
	 * @param channelContextFilter
	 * @author tanyaowu
	 */
	Public  static  void  sendToAll ( GroupContext  groupContext ,  Packet  packet ,  ChannelContextFilter  channelContextFilter )  {
		sendToAll ( groupContext ,  packet ,  channelContextFilter ,  false );
	}

	/**
	 *
	 * @param groupContext
	 * @param packet
	 * @param channelContextFilter
	 * @param isBlock
	 * @author tanyaowu
	 */
	Private  static  Boolean  sendToAll ( GroupContext  groupContext ,  Packet  packet ,  ChannelContextFilter  channelContextFilter ,  boolean  isBlock )  {
		ObjWithLock < Set < ChannelContext >>  setWithLock  =  groupContext . connections . getSetWithLock ();
		If  ( setWithLock  ==  null )  {
			Log . debug ( "{}, no connection" ,  groupContext . getName ());
			Return  false ;
		}

		Return  sendToSet ( groupContext ,  setWithLock ,  packet ,  channelContextFilter ,  isBlock );
	}

	/**
	 * Send a message to the group
	 * @param groupContext
	 * @param group
	 * @param packet
	 * @author tanyaowu
	 */
	Public  static  void  sendToGroup ( GroupContext  groupContext ,  String  group ,  Packet  packet )  {
		sendToGroup ( groupContext ,  group ,  packet ,  null );
	}

	/**
	 * Send a message to the group
	 * @param groupContext
	 * @param group
	 * @param packet
	 * @param channelContextFilter
	 * @author tanyaowu
	 */
	Public  static  void  sendToGroup ( GroupContext  groupContext ,  String  group ,  Packet  packet ,  ChannelContextFilter  channelContextFilter )  {
		sendToGroup ( groupContext ,  group ,  packet ,  channelContextFilter ,  false );
	}

	/**
	 * Send a message to the group
	 * @param groupContext
	 * @param group
	 * @param packet
	 * @param channelContextFilter
	 * @author tanyaowu
	 */
	Private  static  Boolean  sendToGroup ( GroupContext  groupContext ,  String  group ,  Packet  packet ,  ChannelContextFilter  channelContextFilter ,  boolean  isBlock )  {
		ObjWithLock < Set < ChannelContext >>  setWithLock  =  groupContext . groups . clients ( groupContext ,  group );
		If  ( setWithLock  ==  null )  {
			Log . debug ( "{}, group [{}] does not exist" ,  groupContext . getName (),  group );
			Return  false ;
		}
		Return  sendToSet ( groupContext ,  setWithLock ,  packet ,  channelContextFilter ,  isBlock );
	}
	
	/**
	 * Block the send to the specified ip corresponding collection
	 * @param groupContext
	 * @param ip
	 * @param packet
	 * @author: tanyaowu
	 */
	Public  static  void  bSendToIp ( GroupContext  groupContext ,  String  ip ,  Packet  packet )  {
		bSendToIp ( groupContext ,  ip ,  packet ,  null );
	}

	/**
	 * Block the send to the specified ip corresponding collection
	 * @param groupContext
	 * @param ip
	 * @param packet
	 * @param channelContextFilter
	 * @return
	 * @author: tanyaowu
	 */
	Public  static  Boolean  bSendToIp ( GroupContext  groupContext ,  String  ip ,  Packet  packet ,  ChannelContextFilter  channelContextFilter )  {
		Return  sendToIp ( groupContext ,  ip ,  packet ,  channelContextFilter ,  true );
	}

	/**
	 * Send to the collection corresponding to the specified ip
	 * @param groupContext
	 * @param ip
	 * @param packet
	 * @author: tanyaowu
	 */
	Public  static  void  sendToIp ( GroupContext  groupContext ,  String  ip ,  Packet  packet )  {
		sendToIp ( groupContext ,  ip ,  packet ,  null );
	}

	/**
	 * Send to the collection corresponding to the specified ip
	 * @param groupContext
	 * @param ip
	 * @param packet
	 * @param channelContextFilter
	 * @author: tanyaowu
	 */
	Public  static  void  sendToIp ( GroupContext  groupContext ,  String  ip ,  Packet  packet ,  ChannelContextFilter  channelContextFilter )  {
		sendToIp ( groupContext ,  ip ,  packet ,  channelContextFilter ,  false );
	}

	/**
	 * Send to the collection corresponding to the specified ip
	 * @param groupContext
	 * @param ip
	 * @param packet
	 * @param channelContextFilter
	 * @param isBlock
	 * @return
	 * @author: tanyaowu
	 */
	Private  static  Boolean  sendToIp ( GroupContext  groupContext ,  String  ip ,  Packet  packet ,  ChannelContextFilter  channelContextFilter ,  boolean  isBlock )  {
		ObjWithLock < Set < ChannelContext >>  setWithLock  =  groupContext . ips . clients ( groupContext ,  ip );
		If  ( setWithLock  ==  null )  {
			Log . info ( "{}, no ip is the opposite of [{}]" ,  groupContext . getName (),  ip );
			Return  false ;
		}

		Return  sendToSet ( groupContext ,  setWithLock ,  packet ,  channelContextFilter ,  isBlock );
	}

	/**
	 * Send a message to the specified ChannelContext id
	 * @param channelId
	 * @param packet
	 * @author tanyaowu
	 */
	Public  static  Boolean  sendToId ( GroupContext  groupContext ,  String  channelId ,  Packet  packet )  {
		Return  sendToId ( groupContext ,  channelId ,  packet ,  false );
	}

	/**
	 * Send a message to the specified ChannelContext id
	 * @param channelId
	 * @param packet
	 * @param isBlock
	 * @return
	 * @author tanyaowu
	 */
	Private  static  Boolean  sendToId ( GroupContext  groupContext ,  String  channelId ,  Packet  packet ,  boolean  isBlock )  {
		ChannelContext  channelContext  =  Aio . getChannelContextById ( groupContext ,  channelId );
		If  ( channelContext  ==  null )  {
			Return  false ;
		}
		If  ( isBlock )  {
			Return  bSend ( channelContext ,  packet );
		}  else  {
			Return  send ( channelContext ,  packet );
		}
	}

	/**
	 * Send a message to the specified collection
	 * @param groupContext
	 * @param setWithLock
	 * @param packet
	 * @param channelContextFilter
	 * @author tanyaowu
	 */
	Public  static  void  sendToSet ( GroupContext  groupContext ,  ObjWithLock < Set < ChannelContext >>  setWithLock ,  Packet  packet ,  ChannelContextFilter  channelContextFilter )  {
		sendToSet ( groupContext ,  setWithLock ,  packet ,  channelContextFilter ,  false );
	}

	/**
	 * Send a message to the specified collection
	 * @param groupContext
	 * @param setWithLock
	 * @param packet
	 * @param channelContextFilter
	 * @param isBlock
	 * @author tanyaowu
	 */
	Private  static  Boolean  sendToSet ( GroupContext  groupContext ,  ObjWithLock < Set < ChannelContext >>  setWithLock ,  Packet  packet ,  ChannelContextFilter  channelContextFilter ,
			Boolean  isBlock )  {
		// if (isBlock)
		// {
		// try
		// {
		// org.tio.core.GroupContext.SYN_SEND_SEMAPHORE.acquire();
		// } catch (InterruptedException e)
		// {
		// log.error(e.toString(), e);
		// }
		// }

		Lock  lock  =  setWithLock . getLock (). readLock ();
		Boolean  releasedLock  =  false ;
		Try  {
			Lock . lock ();
			Set < ChannelContext >  set  =  setWithLock . getObj ();
			If  ( set . size ()  ==  0 )  {
				Log . debug ( "{}, collection is empty" ,  groupContext . getName ());
				Return  false ;
			}
			If  (! groupContext . isEncodeCareWithChannelContext ())  {
				ByteBuffer  byteBuffer  =  groupContext . getAioHandler (). encode ( packet ,  groupContext ,  null );
				Packet . setPreEncodedByteBuffer ( byteBuffer );
			}

			CountDownLatch  countDownLatch  =  null ;
			If  ( isBlock )  {
				countDownLatch  =  new  CountDownLatch ( set . size ());
			}
			Int  sendCount  =  0 ;
			For  ( ChannelContext  channelContext  :  set )  {
				If  ( channelContextFilter  !=  null )  {
					Boolean  isfilter  =  channelContextFilter . filter ( channelContext );
					If  (! isfilter )  {
						If  ( isBlock )  {
							countDownLatch . countDown ();
						}
						Continue ;
					}
				}

				sendCount ++;
				If  ( isBlock )  {
					channelContext . traceBlockPacket ( SynPacketAction . BEFORE_WAIT ,  packet ,  countDownLatch ,  null );
					Send ( channelContext ,  packet ,  countDownLatch ,  PacketSendMode . GROUP_BLOCK );
				}  else  {
					Send ( channelContext ,  packet ,  null ,  null );
				}
			}
			Lock . unlock ();
			releasedLock  =  true ;

			If  ( sendCount  ==  0 )  {
				Return  false ;
			}

			If  ( isBlock )  {
				Try  {
					Long  timeout  =  sendCount  /  5 ;
					Timeout  =  timeout  <  10  ?  10  :  timeout ;
					Boolean  awaitFlag  =  countDownLatch . await ( timeout ,  TimeUnit . SECONDS );
					If  (! awaitFlag )  {
						Log . error ( "{}, sync group timeout, size:{}, timeout:{}, packet:{}" ,  groupContext . getName (),  setWithLock . getObj (). size (),  timeout ,  packet . logstr ( ));
						Return  false ;
					}  else  {
						Return  true ;
					}
				}  catch  ( InterruptedException  e )  {
					Log . error ( e . toString ( ),  e );
					Return  false ;
				}  finally  {

				}
			}  else  {
				Return  true ;
			}
		}  catch  ( Throwable  e )  {
			Log . error ( e . toString ( ),  e );
			Return  false ;
		}  finally  {
			// if (isBlock)
			// {
			// org.tio.core.GroupContext.SYN_SEND_SEMAPHORE.release();
			// }
			If  (! releasedLock )  {
				Lock . unlock ();
			}
		}
	}

	/**
	 * Send a message to the specified user
	 * @param groupContext
	 * @param userid
	 * @param packet
	 * @author tanyaowu
	 */
	Public  static  Boolean  sendToUser ( GroupContext  groupContext ,  String  userid ,  Packet  packet )  {
		Return  sendToUser ( groupContext ,  userid ,  packet ,  false );
	}
	
	/**
	 * Send a message to the specified token
	 * @param groupContext
	 * @param token
	 * @param packet
	 * @return
	 * @author tanyaowu
	 */
	Public  static  Boolean  sendToToken ( GroupContext  groupContext ,  String  token ,  Packet  packet )  {
		Return  sendToToken ( groupContext ,  token ,  packet ,  false );
	}

	/**
	 * Send a message to the specified user
	 * @param groupContext
	 * @param userid
	 * @param packet
	 * @param isBlock
	 * @author tanyaowu
	 */
	Private  static  Boolean  sendToUser ( GroupContext  groupContext ,  String  userid ,  Packet  packet ,  boolean  isBlock )  {
		SetWithLock < ChannelContext >  setWithLock  =  groupContext . users . find ( groupContext ,  userid );
		Try  {
			If  ( setWithLock  ==  null )  {
				Return  false ;
			}
			
			ReadLock  readLock  =  setWithLock . getLock (). readLock ();
			readLock . lock ();
			Try  {
				Set < ChannelContext >  set  =  setWithLock . getObj ();
				Boolean  ret  =  false ;
				For  ( ChannelContext  channelContext  :  set )  {
					Boolean  singleRet  =  false ;
					// Don't use a = a || b() to easily leak the following function
					If  ( isBlock )  {
						singleRet  =  bSend ( channelContext ,  packet );
					}  else  {
						singleRet  =  send ( channelContext ,  packet );
					}
					If  ( singleRet )  {
						Ret  =  true ;
					}
				}
				Return  ret ;
			}  catch  ( Throwable  e )  {
				Log . error ( e . getMessage (),  e );
			}  finally  {
				readLock . unlock ();
			}
			Return  false ;
		}  finally  {
			
		}
	}
	
	/**
	 * Send a message to the specified token
	 * @param groupContext
	 * @param token
	 * @param packet
	 * @param isBlock
	 * @author tanyaowu
	 */
	Private  static  Boolean  sendToToken ( GroupContext  groupContext ,  String  token ,  Packet  packet ,  boolean  isBlock )  {
		SetWithLock < ChannelContext >  setWithLock  =  groupContext . tokens . find ( groupContext ,  token );
		Try  {
			If  ( setWithLock  ==  null )  {
				Return  false ;
			}
			
			ReadLock  readLock  =  setWithLock . getLock (). readLock ();
			readLock . lock ();
			Try  {
				Set < ChannelContext >  set  =  setWithLock . getObj ();
				Boolean  ret  =  false ;
				For  ( ChannelContext  channelContext  :  set )  {
					Boolean  singleRet  =  false ;
					// Don't use a = a || b() to easily leak the following function
					If  ( isBlock )  {
						singleRet  =  bSend ( channelContext ,  packet );
					}  else  {
						singleRet  =  send ( channelContext ,  packet );
					}
					If  ( singleRet )  {
						Ret  =  true ;
					}
				}
				Return  ret ;
			}  catch  ( Throwable  e )  {
				Log . error ( e . getMessage (),  e );
			}  finally  {
				readLock . unlock ();
			}
			Return  false ;
		}  finally  {
			
		}
	}

	/**
	 * Send and wait for a response.<br>
	 * Note: <br>
	 * 1, the synSeq of the parameter packet is not empty and greater than 0 (null, equal to less than 0 will not work) <br>
	 * 2, after receiving the message, the peer needs to return a message like synSeq<br>
	 * 3. For synchronous sending, the framework layer does not help the application to call the handler.handler(packet, channelContext) method. The application needs to process the response packet by itself. Reference: groupContext.getAioHandler().handler(packet, channelContext) ;<br>
	 *
	 * @param channelContext
	 * @param packet
	 * @param timeout
	 * @return
	 * @author tanyaowu
	 */
	@SuppressWarnings ( "finally" )
	Public  static  Packet  synSend ( ChannelContext  channelContext ,  Packet  packet ,  long  timeout )  {
		Integer  synSeq  =  packet . getSynSeq ();
		If  ( synSeq  ==  null  ||  synSeq  <=  0 )  {
			Throw  new  RuntimeException ( "synSeq must be greater than 0" );
		}

		ChannelContextMapWithLock  waitingResps  =  channelContext . getGroupContext (). getWaitingResps ();
		Try  {
			Waiting for Resps . put ( synSeq ,  packet );

			Synchronized  ( packet )  {
				Send ( channelContext ,  packet );
				Try  {
					Packet . wait ( timeout );
				}  catch  ( InterruptedException  e )  {
					Log . error ( e . toString ( ),  e );
				}
			}
		}  catch  ( Throwable  e )  {
			Log . error ( e . toString ( ),  e );
		}  finally  {
			Packet  respPacket  =  waitingResps . remove ( synSeq );
			If  ( respPacket  ==  null )  {
				Log . error ( "respPacket == null,{}" ,  channelContext );
				Return  null ;
			}
			If  ( respPacket  ==  packet )  {
				Log . error ( "{}, synchronous send timeout, {}" ,  channelContext . getGroupContext (). getName (),  channelContext );
				Return  null ;
			}
			Return  respPacket ;
		}
	}

	/**
	 * Untie the relationship with all groups
	 * @param channelContext
	 * @author tanyaowu
	 */
	public  static  void  unbindGroup ( ChannelContext  channelContext )  {
		channelContext . getGroupContext (). Groups . the unbind ( channelContext );
	}

	/**
	 * Unbind from the specified group
	 * @param group
	 * @param channelContext
	 * @author tanyaowu
	 */
	public  static  void  unbindGroup ( String  Group ,  ChannelContext  channelContext )  {
		channelContext . getGroupContext (). Groups . the unbind ( Group ,  channelContext );
	}

	// org.tio.core.GroupContext.ipBlacklist

	/**
	 * Undo the userid of the channelContext binding
	 * @param channelContext
	 * @author tanyaowu
	 */
	Public  static  void  unbindUser ( ChannelContext  channelContext )  {
		channelContext . getGroupContext (). users . unbind ( channelContext );
	}
	
	/**
	 * Undo the token bound to the channelContext
	 * @param channelContext
	 * @author tanyaowu
	 */
	Public  static  void  unbindToken ( ChannelContext  channelContext )  {
		channelContext . getGroupContext (). tokens . unbind ( channelContext );
	}

	/**
	 * Unbind the userid. Generally used for multiple logins, kicking off the previously logged in scenes
	 * @param groupContext
	 * @param userid
	 * @author: tanyaowu
	 */
	Public  static  void  unbindUser ( GroupContext  groupContext ,  String  userid )  {
		groupContext . users . unbind ( groupContext ,  userid );
	}

}
