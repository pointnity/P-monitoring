Package  org . tio . utils . lock ;

Import  java.util.List ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;

/**
 * @author tanyaowu
 * 
 */
Public  class  ListWithLock < T >  extends  ObjWithLock < List < T >>  {
	Private  static  final  long  serialVersionUID  =  8549668315606224029L ;
	Private  static  final  Logger  log  =  LoggerFactory . getLogger ( ListWithLock . class );

	/**
	 * @param durationList
	 * @author tanyaowu
	 */
	Public  ListWithLock ( List < T >  list )  {
		Super ( list );
	}

	/**
	 * @param durationList
	 * @param lock
	 * @author tanyaowu
	 */
	Public  ListWithLock ( List < T >  list ,  ReentrantReadWriteLock  lock )  {
		Super ( list ,  lock );
	}
	
	
	/**
	 *
	 * @param t
	 * @return
	 * @author tanyaowu
	 */
	Public  boolean  add ( T  t )  {
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			List < T >  list  =  this . getObj ();
			Return  list . add ( t );
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
		Return  false ;
	}

	/**
	 *
	 *
	 * @author tanyaowu
	 */
	Public  void  clear ()  {
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			List < T >  list  =  this . getObj ();
			List . clear ();
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
	}

	/**
	 *
	 * @param t
	 * @return
	 * @author tanyaowu
	 */
	Public  boolean  remove ( T  t )  {
		WriteLock  writeLock  =  this . getLock (). writeLock ();
		writeLock . lock ();
		Try  {
			List < T >  list  =  this . getObj ();
			Return  list . remove ( t );
		}  catch  ( Throwable  e )  {
			Log . error ( e . getMessage (),  e );
		}  finally  {
			writeLock . unlock ();
		}
		Return  false ;
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
			List < T >  list  =  this . getObj ();
			Return  list . size ();
		}  finally  {
			readLock . unlock ();
		}
	}
}
