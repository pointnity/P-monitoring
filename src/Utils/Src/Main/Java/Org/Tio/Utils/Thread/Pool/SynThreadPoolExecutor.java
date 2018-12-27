Package  org . tio . utils . thread . pool ;

Import  java.util.concurrent.BlockingQueue ;
Import  java.util.concurrent.Future ;
Import  java.util.concurrent.ThreadFactory ;
Import  java.util.concurrent.ThreadPoolExecutor ;
Import  java.util.concurrent.TimeUnit ;
Import  java.util.concurrent.locks.Lock ;
Import  java.util.concurrent.locks.ReadWriteLock ;

/**
 *
 * @author tanyaowu
 * 
 */
Public  class  SynThreadPoolExecutor  extends  ThreadPoolExecutor  {
	// private static Logger log = LoggerFactory.getLogger(SynThreadPoolExecutor.class);

	/** The name. */
