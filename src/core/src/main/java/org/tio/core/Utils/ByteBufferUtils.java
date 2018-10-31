Package  org . tio . core . utils ;

Import  java.io.UnsupportedEncodingException ;
Import  java.nio.ByteBuffer ;

Import  org.apache.commons.lang3.StringUtils ;
Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.core.exception.LengthOverflowException ;

/**
 * 
 * @author tanyaowu 
 *  
 */
Public  class  ByteBufferUtils  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( ByteBufferUtils . class );

	/**
	 *
	 * @param byteBuffer1
	 * @param byteBuffer2
	 * @return
