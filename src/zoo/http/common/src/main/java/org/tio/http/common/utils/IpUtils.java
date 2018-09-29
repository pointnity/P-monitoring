package org.tio.http.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpRequest;

/**
 *
 * @author tanyaowu
 *  
 */
public class IpUtils {

	/**
	 * Get the native IP
	 * @return Native IP
	 */
	public static String getLocalIp() throws SocketException {
		String localip = null; // Local IP, which is returned if no extranet IP is configured
		String netip = null; //External network IP

		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false; // Whether to find an extranet IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// External network IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// Intranet IP
					localip = ip.getHostAddress();
				}
			}
		}

		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

	/**
