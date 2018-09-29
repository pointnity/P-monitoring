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
