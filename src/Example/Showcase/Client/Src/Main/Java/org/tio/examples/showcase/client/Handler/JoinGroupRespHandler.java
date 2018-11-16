Package  org . tio . examples . showcase . client . handler .

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.Aio ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.JoinGroupRespBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 *  
 */
Public  class  JoinGroupRespHandler  extends  AbsShowcaseBsHandler < JoinGroupRespBody >  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( JoinGroupRespHandler . class );

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
	Public  JoinGroupRespHandler ()  {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
