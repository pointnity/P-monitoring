Package  org . tio . examples . showcase . common ;

/**
 * Message type definition
 * @author tanyaowu
 *  
 */
Public  interface  Type  {

	/**
	/**
	 * Login message request
	 */
	Byte  LOGIN_REQ  =  1 ;
	/**
	 * Login message response
	 */
	Byte  LOGIN_RESP  =  2 ;

	/**
	 * Enter group message request
	 */
	Byte  JOIN_GROUP_REQ  =  3 ;
	/**
	 * Enter group message response
	 */
	Byte  JOIN_GROUP_RESP  =  4 ;

	/**
	 * Peer-to-peer message request
	 */
	Byte  P2P_REQ  =  5 ;
	/**
	 * Point-to-point message response
	 */
	Byte  P2P_RESP  =  6 ;

	/**
	 * Group chat message request
	 */
	Byte  GROUP_MSG_REQ  =  7 ;
	/**
	 * Group chat message response
	 */
	Byte  GROUP_MSG_RESP  =  8 ;

	/**
	 * Heartbeat
	 */
	Byte  HEART_BEAT_REQ  =  99 ;

}
