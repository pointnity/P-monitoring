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

	Public  static  SerializeConfig  put ( Class <?>  clazz ,  SerializeFilter  filter )  {
		Mapping . addFilter ( clazz ,  filter );
		Return  mapping ;
	}
	
	Public  static  SerializeConfig  put ( Class <?>  clazz ,  ObjectSerializer  serializer )  {
		Mapping . put ( clazz ,  serializer );
		Return  mapping ;
	}

	Public  static  < T >  T  toBean ( String  jsonString ,  Class < T >  tt )  {
		Try  {
			If  ( StringUtils . isBlank ( jsonString ))  {
				Return  null ;
			}

			T  t  =  JSON . parseObject ( jsonString ,  tt );
			Return  t ;
		}  catch  ( Throwable  e )  {
			Throw  new  RuntimeException ( e );
		}
	}

	Public  static  String  toFormatedJson ( Object  bean )  {
		Try  {
			Return  JSON . toJSONString ( bean ,  mapping ,  SerializerFeature . DisableCircularReferenceDetect ,  SerializerFeature . PrettyFormat );
		}  catch  ( Throwable  e )  {
