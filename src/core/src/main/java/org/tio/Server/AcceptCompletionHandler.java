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
