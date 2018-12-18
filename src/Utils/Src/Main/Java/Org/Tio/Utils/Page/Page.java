Package  org . tio . utils . page ;

Import  java.io.Serializable ;
Import  java.util.List ;

/**
 *
 * @author tanyaowu
 * 
 */
Public  class  Page < T >  implements  Serializable  {

	Private  static  final  long  serialVersionUID  =  6551482606063638959L ;
	Private  List < T >  pageData  =  null ;  //data of the current page
	Private  Integer  pageIndex ;  //The current page number, starting from 1, if the value is less than or equal to 0, it is regarded as 1
	Private  Integer  pageSize ;  //Number of records per page
	Private  Integer  recordCount ;  // total number of items

	Public  Page ()  {

	}

	/**
	 *
	 * @param pageData
