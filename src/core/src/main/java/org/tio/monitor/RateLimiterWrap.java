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
