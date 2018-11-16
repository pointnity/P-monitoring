Package  org . tio . examples . showcase . client . handler .

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.ShowcaseSessionContext ;
Import  org.tio.examples.showcase.common.intf.AbsShowcaseBsHandler ;
Import  org.tio.examples.showcase.common.packets.LoginRespBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 *  
 */
public  class  LoginRespHandler  the extends  AbsShowcaseBsHandler < LoginRespBody >  {
	Private  static  Logger  log  =  of LoggerFactory . the getLogger ( LoginRespHandler . class );

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
	Public  LoginRespHandler ()  {
	}

	/**
	 * @return
