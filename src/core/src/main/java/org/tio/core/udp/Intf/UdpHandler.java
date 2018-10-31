Package  org . tio . core . udp . intf ;

Import  java.net.DatagramSocket ;

Import  org.tio.core.udp.UdpPacket ;

/**
 * @author tanyaowu
 *  
 */
Public  interface  UdpHandler  {

	/**
	 *
	 * @param udpPacket
	 * @param datagramSocket
	 * @author tanyaowu
	 */
	Public  void  handler ( UdpPacket  udpPacket ,  DatagramSocket  datagramSocket );
}
