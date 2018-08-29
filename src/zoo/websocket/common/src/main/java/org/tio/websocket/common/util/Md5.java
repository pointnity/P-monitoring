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
