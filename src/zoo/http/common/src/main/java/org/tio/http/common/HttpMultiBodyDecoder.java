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
			return name;
		}

		public void setContentDisposition(String contentDisposition) {
			this.contentDisposition = contentDisposition;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public void setMap(Map<String, String> map) {
			this.map = map;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	/**
	 * 【
	 * Content-Disposition: form-data; name="uploadFile"; filename=""
	 * Content-Type: application/octet-stream
	 * 】
	 *
	 * 【
	 * Content-Disposition: form-data; name="end"
	 * 】
	 * @author tanyaowu
	 *  
	 */
	public static interface MultiBodyHeaderKey {
		String Content_Disposition = "Content-Disposition".toLowerCase();
		String Content_Type = "Content-Type".toLowerCase();
	}

	public static enum Step {
		BOUNDARY, HEADER, BODY, END
	}

	private static Logger log = LoggerFactory.getLogger(HttpMultiBodyDecoder.class);

	//    public static int processReadIndex(ByteBuffer buffer)
	//    {
	//        int newReaderIndex = buffer.readerIndex();
	//        if (newReaderIndex < buffer.capacity())
	//        {
	//            buffer.readerIndex(newReaderIndex + 1);
	//            return 1;
	//        }
	//        return 0;
	//    }

	public static void decode(HttpRequest request, RequestLine firstLine, byte[] bodyBytes, String initboundary, ChannelContext channelContext) throws AioDecodeException {
		if (StringUtils.isBlank(initboundary)) {
			throw new AioDecodeException("boundary is null");
		}

		long start = SystemTimer.currentTimeMillis();

		ByteBuffer buffer = ByteBuffer.wrap(bodyBytes);
		buffer.position(0);
