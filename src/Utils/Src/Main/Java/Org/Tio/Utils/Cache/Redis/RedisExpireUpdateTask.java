Package  org . tio . utils . cache . redis ;

Import  java.util.HashSet ;
Import  java.util.Set ;
Import  java.util.concurrent.TimeUnit ;
Import  java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock ;

Import  org.slf4j.Logger ;
Import  org.slf4j.LoggerFactory ;
Import  org.tio.utils.lock.SetWithLock ;

/**
 * Regularly update the expiration time of redis
 * @author tanyaowu
 * 
 */
Public  class  RedisExpireUpdateTask  {
