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
	public static HttpResponse file(HttpRequest request, File fileOnServer) throws IOException {
		Date lastModified = FileUtil.lastModifiedTime(fileOnServer);
		HttpResponse ret = try304(request, lastModified.getTime());
		if (ret != null) {
			return ret;
		}

		byte[] bodyBytes = FileUtil.readBytes(fileOnServer);
		String filename = fileOnServer.getName();
		String extension = FileNameUtil.getExtension(filename);
		ret = file(request, bodyBytes, extension);
		ret.addHeader(HttpConst.ResponseHeaderKey.Last_Modified, lastModified.getTime() + "");
		return ret;
	}

	/**
	 * 
	 * @param request
	 * @param path The relative pageroot path of the file on the server, as in the form: "/user/index.html
	 * @param httpConfig
	 * @return
	 * @throws IOException
	 * @author: tanyaowu
	 */
	public static HttpResponse file(HttpRequest request, String path, HttpConfig httpConfig) throws IOException {
		File pageRoot = httpConfig.getPageRoot();
		if (pageRoot != null) {
//			String root = FileUtil.getAbsolutePath(pageRoot);
			File file = new File(pageRoot + path);
			if (!file.exists()) {
				return resp404(request, request.getRequestLine(), httpConfig);
			}
			return file(request, file);
		} else {
			return resp404(request, request.getRequestLine(), httpConfig);
		}
	}

	/**
	 * 
	 * @param request
	 * @param requestLine
	 * @param httpConfig
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponse resp404(HttpRequest request, RequestLine requestLine, HttpConfig httpConfig) {
		File pageRoot = httpConfig.getPageRoot();
		
		if (pageRoot != null) {
			String file404 = httpConfig.getPage404();
//			String root = FileUtil.getAbsolutePath(pageRoot);
			File file = new File(pageRoot + file404);
			if (file.exists()) {
				HttpResponse ret = Resps.redirect(request, file404 + "?tio_initpath=" + requestLine.getPathAndQuery());
				return ret;
			}
		}
		
		HttpResponse ret = Resps.html(request, "404");
		ret.setStatus(HttpResponseStatus.C404);
		return ret;
	}
	
//	/**
//	 * 
//	 * @param newPath
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 * @author tanyaowu
//	 */
//	public static HttpResponse forward(String newPath, HttpRequest request) throws Exception {
//		RequestLine requestLine  = request.getRequestLine();
//		HttpConfig httpConfig = request.getHttpConfig();
//		
//		httpConfig.getContextPath()
//		
//		if (newPath != null) {
//			requestLine.setPath(newPath);
//		}
//		
//		HttpRequestHandler httpRequestHandler = request.getHttpConfig().getHttpRequestHandler();
//		return httpRequestHandler.handler(request);
//	}

	/**
	 * 
	 * @param request
	 * @param requestLine
	 * @param httpConfig
	 * @param throwable
	 * @return
	 * @author: tanyaowu
	 */
	public static HttpResponse resp500(HttpRequest request, RequestLine requestLine, HttpConfig httpConfig, Throwable throwable) {
		File pageRoot = httpConfig.getPageRoot();
		
		if (pageRoot != null) {
			String file500 = httpConfig.getPage500();
//			String root = FileUtil.getAbsolutePath(pageRoot);
			File file = new File(pageRoot + file500);
			if (file.exists()) {
				HttpResponse ret = Resps.redirect(request, file500 + "?tio_initpath=" + requestLine.getPathAndQuery());
				return ret;
			}
		}
		
		HttpResponse ret = Resps.html(request, "500");
		ret.setStatus(HttpResponseStatus.C500);
		return ret;
	}

	/**
	 *
	 * @param request
	 * @param bodyBytes
	 * @param contentType Shape such as: Application/octet-stream, etc.
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse fileWithContentType(HttpRequest request, byte[] bodyBytes, String contentType) {
		HttpResponse ret = new HttpResponse(request, HttpServerUtils.getHttpConfig(request));
		ret.setBody(bodyBytes, request);
		ret.addHeader(HttpConst.ResponseHeaderKey.Content_Type, contentType);
		return ret;
	}

	/**
	 *
	 * @param request
	 * @param bodyBytes
	 * @param headers
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse fileWithHeaders(HttpRequest request, byte[] bodyBytes, Map<String, String> headers, HttpConfig httpConfig) {
		HttpResponse ret = new HttpResponse(request, httpConfig);
		ret.setBody(bodyBytes, request);
		ret.addHeaders(headers);
		return ret;
	}

	/**
	 *
	 * @param request
	 * @param bodyString
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse html(HttpRequest request, String bodyString) {
		HttpConfig httpConfig = HttpServerUtils.getHttpConfig(request);
		return html(request, bodyString, httpConfig.getCharset());
	}

	/**
	 * Content-Type: text/html; charset=utf-8
	 * @param request
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse html(HttpRequest request, String bodyString, String charset) {
		HttpResponse ret = string(request, bodyString, charset, MimeType.TEXT_HTML_HTML.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * Content-Type: application/javascript; charset=utf-8
	 * @param request
	 * @param bodyString
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse js(HttpRequest request, String bodyString) {
		return js(request, bodyString, HttpServerUtils.getHttpConfig(request).getCharset());
	}

	/**
	 * Content-Type: application/javascript; charset=utf-8
	 * @param request
	 * @param bodyString
	 * @param charset
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse js(HttpRequest request, String bodyString, String charset) {
		HttpResponse ret = string(request, bodyString, charset, MimeType.APPLICATION_JAVASCRIPT_JS.getType() + "; charset=" + charset);
		return ret;
	}

	/**
	 * Content-Type: application/json; charset=utf-8
	 * @param request
	 * @param body
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse json(HttpRequest request, Object body) {
		return json(request, body, HttpServerUtils.getHttpConfig(request).getCharset());
	}

	/**
	 * Content-Type: application/json; charset=utf-8
	 * @param request
	 * @param body
	 * @param charset
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse json(HttpRequest request, Object body, String charset) {
		HttpResponse ret = null;
		if (body == null) {
			ret = string(request, "", charset, MimeType.TEXT_PLAIN_JSON.getType() + "; charset=" + charset);
		} else {
			if (body.getClass() == String.class || ClassUtil.isBasicType(body.getClass())) {
				ret = string(request, body + "", charset, MimeType.TEXT_PLAIN_JSON.getType() + "; charset=" + charset);
			} else {
				ret = string(request, Json.toJson(body), charset, MimeType.TEXT_PLAIN_JSON.getType() + "; charset=" + charset);
			}
		}
		return ret;
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 * redirect
	 * @param request
	 * @param path
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse redirect(HttpRequest request, String path) {
		HttpResponse ret = new HttpResponse(request, HttpServerUtils.getHttpConfig(request));
		ret.setStatus(HttpResponseStatus.C302);
		ret.addHeader(HttpConst.ResponseHeaderKey.Location, path);
		return ret;
	}

	/**
	 * To create a string output
	 * @param request
	 * @param bodyString
	 * @param Content_Type
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse string(HttpRequest request, String bodyString, String Content_Type) {
		return string(request, bodyString, HttpServerUtils.getHttpConfig(request).getCharset(), Content_Type);
	}

	/**
	 * To create a string output
	 * @param request
	 * @param bodyString
	 * @param charset
	 * @param Content_Type
	 * @return
	 * @author tanyaowu
	 */
	public static HttpResponse string(HttpRequest request, String bodyString, String charset, String Content_Type) {
		HttpResponse ret = new HttpResponse(request, HttpServerUtils.getHttpConfig(request));
		if (bodyString != null) {
			try {
				ret.setBody(bodyString.getBytes(charset), request);
			} catch (UnsupportedEncodingException e) {
				log.error(e.toString(), e);
			}
		}
		ret.addHeader(HttpConst.ResponseHeaderKey.Content_Type, Content_Type);
		return ret;
	}

	/**
