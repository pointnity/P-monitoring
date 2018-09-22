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

		String boundary = "--" + initboundary;
		String endBoundary = boundary + "--";

		//        int boundaryLength = boundary.getBytes().length;
		Step step = Step.BOUNDARY;
		//        int bufferLength = buffer.capacity();
		try {
			label1: while (true) {
				if (step == Step.BOUNDARY) {
					String line = ByteBufferUtils.readLine(buffer, request.getCharset(), HttpConfig.MAX_LENGTH_OF_BOUNDARY);
					//                    int offset = HttpMultiBodyDecoder.processReadIndex(buffer);
					if (boundary.equals(line)) {
						step = Step.HEADER;
					} else if (endBoundary.equals(line)) // It's over
					{
						//                        int ss = buffer.readerIndex() + 2 - offset;
						break;
					} else {
						throw new AioDecodeException("line need:" + boundary + ", but is: " + line + "");
					}
				}

				Header multiBodyHeader = new Header();
				if (step == Step.HEADER) {
					List<String> lines = new ArrayList<>(2);
					label2: while (true) {
						String line = ByteBufferUtils.readLine(buffer, request.getCharset(), HttpConfig.MAX_LENGTH_OF_MULTI_HEADER);
						if ("".equals(line)) {
							break label2;
						} else {
							lines.add(line);
						}
					}

					parseHeader(lines, multiBodyHeader, channelContext);
					step = Step.BODY;
				}

				if (step == Step.BODY) {
					Step newParseStep = parseBody(multiBodyHeader, request, buffer, boundary, endBoundary, channelContext);
					step = newParseStep;

					if (step == Step.END) {
						break label1;
					}
				}

			}
		} catch (LengthOverflowException loe) {
			throw new AioDecodeException(loe);
		} catch (UnsupportedEncodingException e) {
			log.error(channelContext.toString(), e);
		} finally {
			long end = SystemTimer.currentTimeMillis();
			long iv = end - start;
			log.info("Parsing time-consuming:{}ms", iv);
		}

	}

	/**
	 * The return value does not include the last\r\n
	 * @param buffer
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	//	public static String getLine(ByteBuffer buffer, String charset) throws UnsupportedEncodingException {
	//		char lastByte = 0; // Previous byte
	//		int initPosition = buffer.position();
	//
	//		while (buffer.hasRemaining()) {
	//			char b = (char) buffer.get();
	//
	//			if (b == '\n') {
	//				if (lastByte == '\r') {
	//					int startIndex = initPosition;
	//					int endIndex = buffer.position() - 2;
	//					int length = endIndex - startIndex;
	//					byte[] dst = new byte[length];
	//
	//					System.arraycopy(buffer.array(), startIndex, dst, 0, length);
	//					String line = new String(dst, charset);
	//					return line;
	//				}
	//			}
	//			lastByte = b;
	//		}
	//		return null;
	//	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws LengthOverflowException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, LengthOverflowException {
		String testString = "hello\r\nddd\r\n";
		ByteBuffer buffer = ByteBuffer.wrap(testString.getBytes());

		String xString = ByteBufferUtils.readLine(buffer, "utf-8");
		System.out.println(xString);
		xString = ByteBufferUtils.readLine(buffer, "utf-8");
		System.out.println(xString);
	}

	/**
	 * 
	 * @param header
	 * @param request
	 * @param buffer
	 * @param boundary
	 * @param endBoundary
	 * @param channelContext
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws LengthOverflowException
	 * @author tanyaowu
	 */
	public static Step parseBody(Header header, HttpRequest request, ByteBuffer buffer, String boundary, String endBoundary, ChannelContext channelContext)
			throws UnsupportedEncodingException, LengthOverflowException {
		int initPosition = buffer.position();

		while (buffer.hasRemaining()) {
			String line = ByteBufferUtils.readLine(buffer, request.getCharset(), HttpConfig.MAX_LENGTH_OF_MULTI_BODY);
			boolean isEndBoundary = endBoundary.equals(line);
			boolean isBoundary = boundary.equals(line);
			if (isBoundary || isEndBoundary) {
				int startIndex = initPosition;
				int endIndex = buffer.position() - line.getBytes().length - 2 - 2;
				int length = endIndex - startIndex;
				byte[] dst = new byte[length];

				System.arraycopy(buffer.array(), startIndex, dst, 0, length);
				String filename = header.getFilename();
				if (filename != null)//The field type is file
				{
