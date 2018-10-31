Package org.tio.core.utils;

Import org.slf4j.Logger;
Import org.slf4j.LoggerFactory;

/**
 * @author tanyaowu 
 * 
 */
Public class DebugUtils
{
	Private static Logger log = LoggerFactory.getLogger(DebugUtils.class);

	/**
	 * 
	 * @author tanyaowu
	 */
	Public DebugUtils()
	{
	}

	Public static void printCost(CostIntf cost, String msg)
	{
		Long start = System.currentTimeMillis();
		Cost.action();
		Long end = System.currentTimeMillis();
		Long iv = end - start;
		Log.error(msg + ", time consuming " + iv + "ms");
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
	Public static void main(String[] args)
	{

	}
