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
	 * @author tanyaowu
	 */
	@Override
	public  Class < LoginRespBody >  bodyClass ()  {
		Return  LoginRespBody . class ;
	}

	/**
	 * @param packet
	 * @param bsBody
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	public  Object  Handler ( ShowcasePacket  Packet ,  LoginRespBody  bsBody ,  ChannelContext  channelContext )  throws  Exception  {
		System . out . println ( "Received login response message:"  +  Json . toJson ( bsBody ));
		If  ( LoginRespBody . Code . SUCCESS . equals ( bsBody . getCode ()))  {
			ShowcaseSessionContext  showcaseSessionContext  =  ( ShowcaseSessionContext )  channelContext . getAttribute ();
			showcaseSessionContext . setToken ( bsBody . getToken ());
			System . out . println ( "Login succeeded, token is:"  +  bsBody . getToken ());
		}

