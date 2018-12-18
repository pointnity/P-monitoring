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
	 * @param pageIndex
	 * @param pageSize
	 * @param recordCount
	 * @author tanyaowu
	 */
	Public  Page ( List < T >  pageData ,  Integer  pageIndex ,  Integer  pageSize ,  Integer  recordCount )  {
		the this . PageData  =  PageData ;
		the this . the pageIndex  =  the pageIndex ;
		the this . the pageSize  =  the pageSize ;
		the this . recordCount  =  recordCount ;
	}

	/**
	 * @return the pageData
	 */
	Public  List < T >  getPageData ()  {
		Return  pageData ;
