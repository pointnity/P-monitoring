package org.tio.monitor;

import java.util.concurrent.atomic.AtomicInteger;

import org.tio.utils.SystemTimer;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author tanyaowu
 *  
 */
Public  class  RateLimiterWrap  {
	// private static Logger log = LoggerFactory.getLogger(RateLimiterWrap.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public  static  void  main ( String []  args )  {

	}

	/**
	 * Frequency control
	 */
	Private  RateLimiter  rateLimiter  =  null ; //RateLimiter.create(3);

	/**
	 * How many warnings have been received during this phase?
	 */
	Private  AtomicInteger  warnCount  =  new  AtomicInteger ();

	/**
	 * How many warnings have been received in total
	 */
	Private  AtomicInteger  allWarnCount  =  new  AtomicInteger ();

	/**
	 * Maximum number of warnings at this stage
	 */
	Private  int  maxWarnCount  =  20 ;

	/**
	 * A total of more warnings
	 */
	Private  int  maxAllWarnCount  =  maxWarnCount  *  10 ;

	/**
	 * Last warning time
	 */
	Private  long  lastWarnTime  =  SystemTimer . currentTimeMillis ();

	/**
	 * Warning clear interval, that is, if there is no warning for such a long time, the number of previous warnings is cleared.
	 */
	Private  int  warnClearInterval  =  1000  *  60  *  60  *  2 ;

	/**
	 *
	 * @param permitsPerSecond QPS
	 * @param warnClearInterval Clear the interval of this phase of warning, reference value 1000 * 60 * 60 * 2, the unit is ms
	 * @param maxWarnCount This stage has the most warnings, the reference value is 10
	 * @param maxAllWarnCount A total of more warnings
	 * @author tanyaowu
	 */
	Public  RateLimiterWrap ( int  permitsPerSecond ,  int  warnClearInterval ,  int  maxWarnCount ,  int  maxAllWarnCount )  {
		the this . rateLimiter  =  RateLimiter . Create ( permitsPerSecond );
		the this . warnClearInterval  =  warnClearInterval ;
		the this . maxWarnCount  =  maxWarnCount ;
		This . maxAllWarnCount  =  maxAllWarnCount ;
	}

	/**
	 * @return the allWarnCount
	 */
	Public  AtomicInteger  getAllWarnCount ()  {
		Return  allWarnCount ;
	}

	/**
	 * @return the lastWarnTime
	 */
	Public  long  getLastWarnTime ()  {
		Return  lastWarnTime ;
	}

	/**
	 * @return the maxAllWarnCount
	 */
	Public  int  getMaxAllWarnCount ()  {
		Return  maxAllWarnCount ;
	}

	/**
	 * @return the maxWarnCount
	 */
	Public  int  getMaxWarnCount ()  {
		Return  maxWarnCount ;
	}

	/**
	 * @return the rateLimiter
	 */
	Public  RateLimiter  getRateLimiter ()  {
		Return  rateLimiter ;
	}

	/**
	 * @return the warnClearInterval
	 */
	Public  int  getWarnClearInterval ()  {
		Return  warnClearInterval ;
	}

	/**
	 * @return the warnCount
	 */
	Public  AtomicInteger  getWarnCount ()  {
		Return  warnCount ;
	}

	/**
	 * @param allWarnCount the allWarnCount to set
	 */
	Public  void  setAllWarnCount ( AtomicInteger  allWarnCount )  {
		This . allWarnCount  =  allWarnCount ;
	}

	/**
	 * @param lastWarnTime the lastWarnTime to set
	 */
	Public  void  setLastWarnTime ( long  lastWarnTime )  {
		the this . lastWarnTime  =  lastWarnTime ;
	}

	/**
	 * @param maxAllWarnCount the maxAllWarnCount to set
	 */
	Public  void  setMaxAllWarnCount ( int  maxAllWarnCount )  {
		This . maxAllWarnCount  =  maxAllWarnCount ;
	}

	/**
	 * @param maxWarnCount the maxWarnCount to set
	 */
	Public  void  setMaxWarnCount ( int  maxWarnCount )  {
		the this . maxWarnCount  =  maxWarnCount ;
	}

	/**
	 * @param rateLimiter the rateLimiter to set
	 */
	Public  void  setRateLimiter ( RateLimiter  rateLimiter )  {
		the this . rateLimiter  =  rateLimiter ;
	}

	/**
	 * @param warnClearInterval the warnClearInterval to set
	 */
	Public  void  setWarnClearInterval ( int  warnClearInterval )  {
	the this . warnClearInterval  =  warnClearInterval ;
	}

	/**
	 * @param warnCount the warnCount to set
	 */
	Public  void  setWarnCount ( AtomicInteger  warnCount )  {
		the this . warnCount  =  warnCount ;
	}

	/**
	 *
	 * @return
	 * 0 position: Get the execution lock according to QPS, false: Did not get the lock<br>
	 * 1 position: Get the execution lock according to the number of warnings, false: Did not get the lock<br>
	 * @author tanyaowu
	 */
	Public  boolean []  tryAcquire ()  {
		Boolean  ret  =  rateLimiter . tryAcquire ();
		If  (! ret )  {
			Synchronized  ( this )  {
