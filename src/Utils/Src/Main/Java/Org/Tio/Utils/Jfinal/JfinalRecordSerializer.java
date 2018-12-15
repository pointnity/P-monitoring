Package  org . tio . utils . jfinal ;

Import  java.io.IOException ;
Import  java.lang.reflect.Type ;
Import  java.util.Map ;

Import  com.alibaba.fastjson.serializer.JSONSerializer ;
Import  com.alibaba.fastjson.serializer.ObjectSerializer ;
Import  com.jfinal.plugin.activerecord.Record ;

/**
 * @author tanyaowu
 * 
 */
Public  class  JfinalRecordSerializer  implements  ObjectSerializer  {
	Public  static  final  JfinalRecordSerializer  INSTANCE  =  new  JfinalRecordSerializer ();

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
	Public  jfinalRecordSerializer ()  {
	}

	/**
	 * @param serializer
	 * @param object
	 * @param fieldName
	 * @param fieldType
	 * @param features
	 * @throws IOException
	 * @author tanyaowu
	 */
	@Override
	Public  void  write ( JSONSerializer  serializer ,  Object  object ,  Object  fieldName ,  Type  fieldType ,  int  features )  throws  IOException  {
		If  ( object  ==  null )  {
			Serializer . OUT . writeNull ();
			Return ;
		}

		Record  record  =  ( Record )  object ;

		Map < String ,  Object >  map  =  record . getColumns ();
		Serializer . write ( map );
	}
}
