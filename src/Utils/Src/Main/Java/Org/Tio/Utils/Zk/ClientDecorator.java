Package  org . tio . utils . zk ;

Import  org.apache.curator.framework.CuratorFramework ;

/**
 * @author tanyaowu 
 *  
 */
Public  interface  ClientDecorator  {
	
	/**
	 * 
	 * @param zclient
	 * @author tanyaowu
	 */
	Public  void  decorate ( CuratorFramework  zclient );

}
