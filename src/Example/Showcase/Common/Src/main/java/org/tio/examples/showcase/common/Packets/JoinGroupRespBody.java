Package  ORG . TiO . examples . Showcase . Common . packets ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * Into the group response
 * @author tanyaowu
 *  
 */
public class JoinGroupRespBody extends BaseBody {
	public static interface Code {
		Integer SUCCESS = 1;
		Integer FAIL = 2;
	}

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(JoinGroupRespBody.class);

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	/ / into the group results, see Code interface, showcase for easy to understand, will return success
	Private  Integer  code ;

	/ / If the group fails, you need to provide msg
	Private  String  msg ;

	Private  String  group ;

	/**
	 *
	 * @author tanyaowu
	 */
	Public  JoinGroupRespBody ()  {

	}

	/**
	 * @return the code
	 */
	Public  Integer  getCode ()  {
		Return  code ;
	}

	/**
	 * @return the group
	 */
	Public  String  getGroup ()  {
		Return  group ;
	}

	/**
	 * @return the msg
	 */
	Public  String  getMsg ()  {
		Return  msg ;
	}

	/**
	 * @param code the code to set
	 */
	Public  void  setCode ( Integer  code )  {
		the this . code  =  code ;
	}

	/**
	 * @param group the group to set
	 */
	Public  void  setGroup ( String  group )  {
