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
	 *
	 * @param src
	 * @param startindex starts at 0
	 * @param endindex
	 * @return
	 *
	 * @author: tanyaowu
	 *
	 */
	Public  static  ByteBuffer  copy ( ByteBuffer  src ,  int  startindex ,  int  endindex )  {
		Int  size  =  endindex  -  startindex ;
		Byte []  dest  =  new  byte [ size ];
		System . arraycopy ( src . array (),  startindex ,  dest ,  0 ,  dest . length );
		ByteBuffer  newByteBuffer  =  ByteBuffer . wrap ( dest );
		Return  newByteBuffer ;
	}

	/**
	 *
	 * @param buffer
	 * @return
	 * @author: tanyaowu
	 */
	Public  static  int  lineEnd ( ByteBuffer  buffer )  throws  LengthOverflowException  {
		Return  lineEnd ( buffer ,  Integer . MAX_VALUE );
	}

	/**
	 *
	 * @param buffer
	 * @param maxlength
	 * @return
	 * @author: tanyaowu
	 */
	Public  static  int  lineEnd ( ByteBuffer  buffer ,  int  maxlength )  throws  LengthOverflowException  {
		Boolean  canEnd  =  false ;
		// int startPosition = buffer.position();
		Int  count  =  0 ;
		While  ( buffer . hasRemaining ())  {
			Byte  b  =  buffer . get ();
			Count ++;
			If  ( count  >  maxlength )  {
				Throw  new  LengthOverflowException ( "maxlength is "  +  maxlength );
			}
			If  ( b  ==  '\r' )  {
				canEnd  =  true ;
			}  else  if  ( b  ==  '\n' )  {
				If  ( canEnd )  {
					Int  endPosition  =  buffer . position ();
					Return  endPosition  -  2 ;
				}
			}  else  {
				canEnd  =  false ;
			}
		}
		Return  - 1 ;
	}

	/**
	 * 
	 * @param buffer
	 * @param endChar ends
	 * @param maxlength

	 * @return
	 * @throws LengthOverflowException
	 * @author tanyaowu
	 */
	Public  static  int  lineEnd ( ByteBuffer  buffer ,  char  endChar ,  int  maxlength )  throws  LengthOverflowException  {
		// int startPosition = buffer.position();
		Int  count  =  0 ;
// int i = 0;
		While  ( buffer . hasRemaining ())  {
			Byte  b  =  buffer . get ();
			Count ++;
			If  ( count  >  maxlength )  {
				Throw  new  LengthOverflowException ( "maxlength is "  +  maxlength );
			}
// if (i == 22) {
// log.error("{}-{}", (char)b, b);
// }
// log.error("{},{}-{}", i++, (char)b, b);
			
			If  (( char ) b  ==  endChar )  {
				Int  endPosition  =  buffer . position ();
				Return  endPosition  -  1 ;
			}
		}
		Return  - 1 ;
	}

	Public  static  byte []  readBytes ( ByteBuffer  buffer ,  int  length )  {
		Byte []  ab  =  new  byte [ length ];
		Buffer . get ( ab );
		Return  ab ;
	}

	/**
	 *
	 * @param buffer
	 * @param charset
	 * @return
	 * @author: tanyaowu
	 */
	Public  static  String  readLine ( ByteBuffer  buffer ,  String  charset )  throws  LengthOverflowException  {
		Return  readLine ( buffer ,  charset ,  Integer . MAX_VALUE );
	}

	/**
	 *
	 * @param buffer
	 * @param charset
	 * @param maxlength
	 * @return
	 * @author: tanyaowu
	 */
	Public  static  String  readLine ( ByteBuffer  buffer ,  String  charset ,  Integer  maxlength )  throws  LengthOverflowException  {
		// boolean canEnd = false;
		Int  startPosition  =  buffer . position ();
		Int  endPosition  =  lineEnd ( buffer ,  maxlength );

		If  ( endPosition  >  startPosition )  {
			Byte []  bs  =  new  byte [ endPosition  -  startPosition ];
			System . arraycopy ( buffer . array ( ),  startPosition ,  bs ,  0 ,  bs . length );
			If  ( StringUtils . isNoneBlank ( charset ))  {
				Try  {
					Return  new  String ( bs ,  charset );
				}  catch  ( UnsupportedEncodingException  e )  {
					Throw  new  RuntimeException ( e );
				}
			}  else  {
				Return  new  String ( bs );
			}

		}  else  if  ( endPosition  ==  - 1 )  {
			Return  null ;
		}  else  if  ( endPosition  ==  startPosition )  {
			Return  "" ;
		}
		Return  null ;
	}

	Public  static  String  readLine ( ByteBuffer  buffer ,  String  charset ,  char  endChar ,  Integer  maxlength )  throws  LengthOverflowException  {
		// boolean canEnd = false;
		Int  startPosition  =  buffer . position ();
		Int  endPosition  =  lineEnd ( buffer ,  endChar ,  maxlength );

		If  ( endPosition  >  startPosition )  {
			Byte []  bs  =  new  byte [ endPosition  -  startPosition ];
			System . arraycopy ( buffer . array ( ),  startPosition ,  bs ,  0 ,  bs . length );
			If  ( StringUtils . isNoneBlank ( charset ))  {
				Try  {
					Return  new  String ( bs ,  charset );
				}  catch  ( UnsupportedEncodingException  e )  {
					Throw  new  RuntimeException ( e );
				}
			}  else  {
				Return  new  String ( bs );
			}

		}  else  if  ( endPosition  ==  - 1 )  {
			Return  null ;
		}  else  if  ( endPosition  ==  startPosition )  {
			Return  "" ;
		}
		Return  null ;
	}

