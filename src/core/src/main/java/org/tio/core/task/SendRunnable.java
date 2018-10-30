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

	/**
	 * Clear message queue
	 */
	@Override
	Public  void  clearMsgQueue ()  {
		Object  p  =  null ;
		While  (( p  =  msgQueue . poll ())  !=  null )  {
			Try  {
				channelContext . processAfterSent ( p ,  false );
			}  catch  ( Throwable  e )  {
				Log . error ( e . toString ( ),  e );
			}
		}
	}

	Private  ByteBuffer  getByteBuffer ( Packet  packet ,  GroupContext  groupContext ,  AioHandler  aioHandler )  {
		ByteBuffer  byteBuffer  =  packet . getPreEncodedByteBuffer ();
		If  ( byteBuffer  !=  null )  {
			byteBuffer  =  byteBuffer . duplicate ();
		}  else  {
			byteBuffer  =  aioHandler . encode ( packet ,  groupContext ,  channelContext );
		}
		Return  byteBuffer ;
	}

	@Override
	Public  void  runTask ()  {
		Int  queueSize  =  msgQueue . size ();
		If  ( queueSize  ==  0 )  {
			Return ;
		}
		If  ( queueSize  >=  2000 )  {
			queueSize  =  1000 ;
		}

		//Packet or PacketWithMeta
		Object  obj  =  null ;
		Packet  p  =  null ;
		PacketWithMeta  packetWithMeta  =  null ;
		GroupContext  groupContext  =  this . channelContext . getGroupContext ();
		AioHandler  aioHandler  =  groupContext . getAioHandler ();

		If  ( queueSize  >  1 )  {
			ByteBuffer []  byteBuffers  =  new  ByteBuffer [ queueSize ];
			Int  allBytebufferCapacity  =  0 ;

			Int  packetCount  =  0 ;
			List < Object >  packets  =  new  ArrayList <>( queueSize );
			For  ( int  i  =  0 ;  i  <  queueSize ;  i ++)  {
				If  (( obj  =  msgQueue . poll ())  !=  null )  {
					Boolean  isPacket  =  obj  instanceof  Packet ;
					If  ( isPacket )  {
						p  =  ( Packet )  obj ;
						Packets . add ( p );
					}  else  {
						packetWithMeta  =  ( PacketWithMeta )  obj ;
						p  =  packetWithMeta . getPacket ();
						packets . the Add ( packetWithMeta );
					}

					ByteBuffer  byteBuffer  =  getByteBuffer ( p ,  groupContext ,  aioHandler );

					channelContext . traceClient ( ChannelAction . BEFORE_SEND ,  p ,  null );

					allBytebufferCapacity  +=  byteBuffer . limit ();
					packetCount ++;
					byteBuffers [ i ]  =  byteBuffer ;
				}  else  {
					Break ;
				}
			}

			ByteBuffer  allByteBuffer  =  ByteBuffer . allocate ( allBytebufferCapacity );
			Byte []  dest  =  allByteBuffer . array ();
			For  ( ByteBuffer  byteBuffer  :  byteBuffers )  {
				If  ( byteBuffer  !=  null )  {
					Int  length  =  byteBuffer . limit ();
					Int  position  =  allByteBuffer . position ();
					System . arraycopy ( byteBuffer . array (),  0 ,  dest ,  position ,  length );
					allByteBuffer . position ( position  +  length );
				}
			}
			sendByteBuffer ( allByteBuffer ,  packetCount ,  packets );
		}  else  {
			If  (( obj  =  msgQueue . poll ())  !=  null )  {
				Boolean  isPacket  =  obj  instanceof  Packet ;
				If  ( isPacket )  {
					p  =  ( Packet )  obj ;
					sendPacket ( p );
				}  else  {
					packetWithMeta  =  ( PacketWithMeta )  obj ;
