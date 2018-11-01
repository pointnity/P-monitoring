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
