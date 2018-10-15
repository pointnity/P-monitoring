package org.tio.http.server.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpConst;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.http.common.MimeType;
import org.tio.http.common.RequestLine;
import org.tio.utils.json.Json;

import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.util.ClassUtil;

import jodd.io.FileNameUtil;

/**
 * @author tanyaowu
 *  
 */
public class Resps {
	private static Logger log = LoggerFactory.getLogger(Resps.class);

	/**
	 * Content-Type: text/css; charset=utf-8
	 * @param request
	 * @param bodyString
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse css(HttpRequest request, String bodyString) {
		return css(request, bodyString, HttpServerUtils.getHttpConfig(request).getCharset());
	}

	/**
	 * Content-Type: text/css; charset=utf-8
	 * @param request
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse css(HttpRequest request, String bodyString, String charset) {
		HttpResponse ret = string(request, bodyString, charset, MimeType.TEXT_CSS_CSS.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * Create responses based on file content
	 * @param request
	 * @param bodyBytes
	 * @param extension
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse file(HttpRequest request, byte[] bodyBytes, String extension) {
		String contentType = null;
		//		String extension = FilenameUtils.getExtension(filename);
		if (StringUtils.isNoneBlank(extension)) {
			MimeType mimeType = MimeType.fromExtension(extension);
			if (mimeType != null) {
				contentType = mimeType.getType();
			} else {
				contentType = "application/octet-stream";
			}
		}
		return fileWithContentType(request, bodyBytes, contentType);
	}

	/**
	 * Create responses based on files
	 * @param request
	 * @param fileOnServer
	 * @return
	 * @throws IOException
	 * @author tanyaowu
	 */
