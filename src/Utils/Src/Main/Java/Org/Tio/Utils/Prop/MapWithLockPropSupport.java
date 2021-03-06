Package  org . tio . utils . prop ;

Import  java.util.HashMap ;
Import  java.util.Map ;
Import  java.util.concurrent.locks.Lock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.lock.MapWithLock ;

/**
 * @author tanyaowu
 * 
 */
Public  class  MapWithLockPropSupport  implements  IPropSupport  {
	@SuppressWarnings ( "unused" )
	Private  static  Logger  log  =  LoggerFactory . getLogger ( MapWithLockPropSupport . class );

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	Private  MapWithLock < String ,  Object >  props  =  null ; //

	/**
	 *
	 * @author tanyaowu
	 */
	Public  MapWithLockPropSupport ()  {
	}

	/**
	 *
	 * @author tanyaowu
	 */
	@Override
	Public  void  clearAttribute ()  {
		initProps ();
		Lock  lock  =  props . getLock (). writeLock ();
		Map < String ,  Object >  m  =  props . getObj ();
		Try  {
			Lock . lock ();
			m . clear ();
		}  catch  ( Throwable  e )  {
			Throw  e ;
		}  finally  {
			Lock . unlock ();
		}
	}

	/**
	 *
	 * @param key
	 * @return
	 * @author tanyaowu
	 */
	@Override
	Public  Object  getAttribute ( String  key )  {
		initProps ();
		Map < String ,  Object >  m  =  props . getObj ();
		Object  ret  =  m . get ( key );
		Return  ret ;

		// Lock lock = props.getLock().readLock();
		// Map<String, Object> m = props.getObj();
		// try {
		// lock.lock();
		// Object ret = m.get(key);
		// return ret;
		// } catch (Throwable e) {
		// throw e;
		// } finally {
		// lock.unlock();
		// }
	}

	Private  void  initProps ()  {
		If  ( props  ==  null )  {
			Synchronized  ( this )  {
				If  ( props  ==  null )  {
					The props  =  new new  MapWithLock <> ( new new  the HashMap < String ,  Object > ( 10 ));
				}
			}
		}
	}

	/**
	 * @param key
	 * @author tanyaowu
	 */
	@Override
	Public  void  removeAttribute ( String  key )  {
		initProps ();
		Lock  lock  =  props . getLock (). writeLock ();
		Map < String ,  Object >  m  =  props . getObj ();
		Try  {
			lock.lock();
			m.remove(key);
		} catch (Throwable e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @author tanyaowu
	 */
	@Override
	public void setAttribute(String key, Object value) {
		initProps();
		Lock lock = props.getLock().writeLock();
		Map<String, Object> m = props.getObj();
		try {
			lock.lock();
			m.put(key, value);
		} catch (Throwable e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}
}
