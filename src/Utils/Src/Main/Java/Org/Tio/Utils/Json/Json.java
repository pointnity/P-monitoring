Package  org . tio . utils . json ;

Import  java.util.Date ;

Import  org.apache.commons.lang3.StringUtils ;

Import  com.alibaba.fastjson.JSON ;
Import  com.alibaba.fastjson.serializer.ObjectSerializer ;
Import  com.alibaba.fastjson.serializer.SerializeConfig ;
Import  com.alibaba.fastjson.serializer.SerializeFilter ;
Import  com.alibaba.fastjson.serializer.SerializerFeature ;
Import  com.alibaba.fastjson.serializer.SimpleDateFormatSerializer ;

/**
 *
 * @author tanyaowu
 *  
 */
Public  class  Json  {
	Private  static  SerializeConfig  mapping  =  new  SerializeConfig ();

	Static  {
		Mapping . put ( Date . class ,  new  SimpleDateFormatSerializer ( "yyyy-MM-dd HH:mm:ss" ));
		Mapping . put ( java . sql . Date . class ,  new  SimpleDateFormatSerializer ( "yyyy-MM-dd HH:mm:ss" ));
		Mapping . put ( java . sql . Timestamp . class ,  new  SimpleDateFormatSerializer ( "yyyy-MM-dd HH:mm:ss" ));
		Mapping . put ( java . sql . Time . class ,  new  SimpleDateFormatSerializer ( "HH:mm:ss" ));
	}
