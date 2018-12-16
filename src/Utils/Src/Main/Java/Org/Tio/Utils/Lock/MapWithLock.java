Package  org . tio . utils . lock ;

Import  java.util.Map ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 *  
 */
Public  class  MapWithLock < K ,  V >  extends  ObjWithLock < Map < K ,  V >>  {
	Private  static  final  long  serialVersionUID  =  - 652862323697152866L ;
	Private  static  final  Logger  log  =  LoggerFactory . getLogger ( MapWithLock . class );

	/**
	 * @param cacheMap
	 * @author tanyaowu
	 */
	Public  MapWithLock ( Map < K ,  V >  map )  {
		Super ( map );
	}

	/**
	 * @param cacheMap
	 * @param lock
	 * @author tanyaowu
	 */
	Public  MapWithLock ( Map < K ,  V >  map ,  ReentrantReadWriteLock  lock )  {
		Super ( map ,  lock );
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @author tanyaowu
	 */
	Public  V  put ( K  key ,  V  value )  {
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			Map < K ,  V >  map  =  this . getObj ();
			Return  map . put ( key ,  value );
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
		Return  null ;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @author tanyaowu
	 */
	Public  V  putIfAbsent ( K  key ,  V  value )  {
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			Map < K ,  V >  map  =  this . getObj ();
			V  oldValue  =  map . putIfAbsent ( key ,  value );
			If  ( oldValue  ==  null )  {
				Return  value ;
			}  else  {
				Return  oldValue ;
			}
			
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
		Return  null ;
	}

	/**
	 * 
	 * @param otherMap
	 * @author tanyaowu
	 */
	Public  void  putAll ( Map < K ,  V >  otherMap )  {
		If  ( otherMap  ==  null  ||  otherMap . isEmpty ())  {
			Return ;
		}
		
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			Map < K ,  V >  map  =  this . getObj ();
			Map . putAll ( otherMap );
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @author tanyaowu
	 */
	Public  V  remove ( K  key )  {
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			Map < K ,  V >  map  =  this . getObj ();
			Return  map . remove ( key );
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
		Return  null ;
	}

	/**
	 * clear
	 * @author tanyaowu
	 */
	Public  void  clear ()  {
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			Map < K ,  V >  map  =  this . getObj ();
			Map . clear ();
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @author tanyaowu
	 */
	Public  v  get ( K  key )  {
		ReadLock  readLock  =  this . getLock (). readLock ();
		readLock . lock ();
		Try  {
			Map < K ,  V >  map  =  this . getObj ();
			Return  map . get ( key );
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			readLock . unlock ();
		}
		Return  null ;
	}
	
	/**
	 * 
	 * @return
	 * @author tanyaowu
	 */
	Public  int  size ()  {
		ReadLock  readLock  =  this . getLock (). readLock ();
		readLock . lock ();
		Try  {
