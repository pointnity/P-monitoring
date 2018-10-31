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
	 * @author: tanyaowu
	 */
	Public  static  ByteBuffer  composite ( ByteBuffer  byteBuffer1 ,  ByteBuffer  byteBuffer2 )  {
		Int  capacity  =  byteBuffer1 . limit ()  -  byteBuffer1 . position ()  +  byteBuffer2 . limit ()  -  byteBuffer2 . position ();
		ByteBuffer  ret  =  ByteBuffer . allocate ( capacity );

		Ret . put ( byteBuffer1 );
		Ret . put ( byteBuffer2 );

		Ret . position ( 0 );
		Ret . limit ( ret . capacity ());
		Return  ret ;
	}

	Public  static  void  copy ( ByteBuffer  src ,  int  srcStartindex ,  ByteBuffer  dest ,  int  destStartIndex ,  int  length )  {
		System . arraycopy ( src . array (),  srcStartindex ,  dest . array (),  destStartIndex ,  length );
	}

	/**
