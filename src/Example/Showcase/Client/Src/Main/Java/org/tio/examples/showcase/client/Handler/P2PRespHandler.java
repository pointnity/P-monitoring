Package  org . tio . examples . showcase . client . handler .

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.P2PRespBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 *  
 */
Public  class  P2PRespHandler  extends  AbsShowcaseBsHandler < P2PRespBody >  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( P2PRespHandler . class );

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	Public  P2PRespHandler ()  {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Class < P2PRespBody >  bodyClass ()  {
		Return  P2PRespBody . class ;
	}

	/**
