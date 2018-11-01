Package  org . tio . server ;

Import  java.net.InetSocketAddress ;
Import  java.net.StandardSocketOptions ;
Import  java.nio.ByteBuffer ;
Import  java.nio.channels.AsynchronousServerSocketChannel ;
Import  java.nio.channels.AsynchronousSocketChannel ;
Import  java.nio.channels.CompletionHandler ;
Import  java.util.List ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelAction ;
Import  org.tio.core.ReadCompletionHandler ;
Import  org.tio.core.stat.IpStat ;
Import  org.tio.server.intf.ServerAioListener ;
Import  org.tio.utils.SystemTimer ;

/**
 *
 * @author tanyaowu
 * 
 */
Public  class  AcceptCompletionHandler  implements  CompletionHandler < AsynchronousSocketChannel ,  AioServer >  {

	Private  static  Logger  log  =  LoggerFactory . getLogger ( AcceptCompletionHandler . class );

	Public  AcceptCompletionHandler ()  {
	}

	/**
	 *
	 * @param asynchronousSocketChannel
	 * @param aioServer
	 * @author tanyaowu
	 */
	@Override
	Public  void  completed ( AsynchronousSocketChannel  asynchronousSocketChannel ,  AioServer  aioServer )  {
		Try  {
			ServerGroupContext  serverGroupContext  =  aioServer . getServerGroupContext ();
			InetSocketAddress  inetSocketAddress  =  ( InetSocketAddress )  asynchronousSocketChannel . getRemoteAddress ();
			String  clientIp  =  inetSocketAddress . getHostString ();
// serverGroupContext.ips.get(clientIp).getRequestCount().incrementAndGet();
			
// GuavaCache[] caches = serverGroupContext.ips.getCaches();
// for (GuavaCache guavaCache : caches) {
// IpStat ipStat = (IpStat) guavaCache.get(clientIp);
// ipStat.getRequestCount().incrementAndGet();
// }
			
			List < Long >  list  =  serverGroupContext . ipStats . durationList ;
			For  ( Long  v  :  list )  {
				IpStat  ipStat  =  ( IpStat )  serverGroupContext . ipStats . get ( v ,  clientIp );
				ipStat . getRequestCount (). incrementAndGet ();
			}

			ServerGroupStat  serverGroupStat  =  serverGroupContext . getServerGroupStat ();
			serverGroupStat . getAccepted (). incrementAndGet ();
			
			
// channelContext.getIpStat().getActivatedCount().incrementAndGet();
// for (GuavaCache guavaCache : caches) {
// IpStat ipStat = (IpStat) guavaCache.get(clientIp);
// ipStat.getActivatedCount().incrementAndGet();
// }
// for (Long v : list) {
// IpStat ipStat = (IpStat) serverGroupContext.ips.get(v, clientIp);
// IpStat.getActivatedCount().incrementAndGet();
// }
// IpStat.getActivatedCount(clientIp, true).incrementAndGet();

			asynchronousSocketChannel . setOption ( StandardSocketOptions . SO_REUSEADDR ,  true );
			asynchronousSocketChannel . setOption ( StandardSocketOptions . SO_RCVBUF ,  32  *  1024 );
			asynchronousSocketChannel . setOption ( StandardSocketOptions . SO_SNDBUF ,  32  *  1024 );
			asynchronousSocketChannel . setOption ( StandardSocketOptions . SO_KEEPALIVE ,  true );

			ServerChannelContext  channelContext  =  new  ServerChannelContext ( serverGroupContext ,  asynchronousSocketChannel );
			channelContext . setClosed ( false );
			channelContext . setServerNode ( aioServer . getServerNode ());
			ServerAioListener  serverAioListener  =  serverGroupContext . getServerAioListener ();
			channelContext . getStat (). setTimeFirstConnected ( SystemTimer . currentTimeMillis ());
			
			channelContext . traceClient ( ChannelAction . CONNECT ,  null ,  null );
