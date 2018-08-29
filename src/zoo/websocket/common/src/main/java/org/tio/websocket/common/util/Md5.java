package org.tio.websocket.common.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author tanyaowu
 * 
 */
public class Md5 {

	/**
	 *
	 * @param content
	 * @param charset
	 * @return
	 * @author tanyaowu
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5 An error occurred during the signature process, the specified encoding set is incorrect, and the encoding set you currently specify is:" + charset);
		}
	}

	public static String getMD5(String input) {
	return sign(input, "", "utf-8");
	}

	/**
	 * Signature string
	 * @param text A string that needs to be signed
	 * @param key Secret key
