package org.tio.http.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.exception.LengthOverflowException;
import org.tio.core.utils.ByteBufferUtils;
import org.tio.http.common.utils.HttpParseUtils;
import org.tio.utils.SystemTimer;

/**
 * @author tanyaowu
 *  
 */
public class HttpMultiBodyDecoder {
	public static class Header {
		private String contentDisposition = "form-data";
		private String name = null;
		private String filename = null;
		private String contentType = null;

		private Map<String, String> map = new HashMap<>();

		public String getContentDisposition() {
			return contentDisposition;
		}

		public String getContentType() {
			return contentType;
		}

		public String getFilename() {
			return filename;
		}

		public Map<String, String> getMap() {
			return map;
		}

		public String getName() {
