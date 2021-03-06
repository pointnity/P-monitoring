Package  org . tio . examples . showcase . common . intf ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.ChannelContext ;
Import  org.tio.examples.showcase.common.Const ;
Import  org.tio.examples.showcase.common.ShowcasePacket ;
Import  org.tio.examples.showcase.common.packets.BaseBody ;
Import  org.tio.utils.json.Json ;

/**
 * @author tanyaowu
 *  
 */
Public  abstract  class  AbsShowcaseBsHandler < T  extends  BaseBody >  implements  ShowcaseBsHandlerIntf  {
	Private  static  Logger  log  =  LoggerFactory . getLogger ( AbsShowcaseBsHandler . class );

	/**
	 *
	 * @author tanyaowu
	 */
	Public  AbsShowcaseBsHandler ()  {
	}

	Public  abstract  Class < T >  bodyClass ();

	@Override
	Public  Object  handler ( ShowcasePacket  packet ,  ChannelContext  channelContext )  throws  Exception  {
		String  jsonStr  =  null ;
		T  bsBody  =  null ;
	If  ( packet . getBody ()  !=  null )  {
		jsonStr  =  new  String ( packet . getBody (),  Const . CHARSET );
			bsBody  =  Json . toBean ( jsonStr ,  bodyClass ());
	}

		Return  handler ( packet ,  bsBody ,  channelContext );
	}

	Public  abstract  Object  handler ( ShowcasePacket  packet ,  T  bsBody ,  ChannelContext  channelContext )  throws  Exception ;

}
