package org.tio.http.server.demo1.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.UploadFile;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.demo1.model.User;
import org.tio.http.server.util.Resps;
import org.tio.utils.json.Json;

import com.xiaoleilu.hutool.io.FileUtil;

/**
 * @author tanyaowu
 *  
 */
@RequestPath(value = "/test")
public class TestController {
	private static Logger log = LoggerFactory.getLogger(TestController.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	String html = "<div style='position:relation;border-radius:10px;text-align:center;padding:10px;font-size:40pt;font-weight:bold;background-color:##e4eaf4;color:#2d8cf0;border:0px solid #2d8cf0; width:600px;height:400px;margin:auto;box-shadow: 1px 1px 50px #000;position: fixed;top:0;left:0;right:0;bottom:0;'>"
			+ "<a style='text-decoration:none' href='https://gitee.com/tywo45/t-io' target='_blank'>"
			+ "<div style='text-shadow: 8px 8px 8px #99e;'>hello tio httpserver</div>" + "</a>" + "</div>";

	String txt = html;

	/**
	 *
	 * @author tanyaowu
	 */
	public TestController() {
	}

	@RequestPath(value = "/abtest")
	public HttpResponse abtest(HttpRequest request) throws Exception {
		HttpResponse ret = Resps.html(request, "OK");
		return ret;
	}

	/**
	 * Test Map Duplication
	 */
	@RequestPath(value = "/abtest")
	public HttpResponse abtest1(HttpRequest request) throws Exception {
		log.info("");
		HttpResponse ret = Resps.html(request, "OK---------1");
		return ret;
	}

	@RequestPath(value = "/bean")
	public HttpResponse bean(User user, HttpRequest request) throws Exception {
		HttpResponse ret = Resps.json(request, Json.toFormatedJson(user));
		return ret;
	}

	@RequestPath(value = "/filetest")
	public HttpResponse filetest(HttpRequest request) throws Exception {
		HttpResponse ret = Resps.file(request, new File("d:/tio.exe"));
		return ret;
	}

	@RequestPath(value = "/filetest.zip")
	public HttpResponse filetest_zip(HttpRequest request) throws Exception {
		HttpResponse ret = Resps.file(request, new File("d:/eclipse-jee-neon-R-win32-x86_64.zip"));
		return ret;
	}

	@RequestPath(value = "/getsession")
	public HttpResponse getsession(HttpRequest request) throws Exception {
		String value = (String) request.getHttpSession().getAttribute("test");
		HttpResponse ret = Resps.json(request, "Gets the value:" + value);
		return ret;
	}

	@RequestPath(value = "/html")
	public HttpResponse html(HttpRequest request) throws Exception {
		HttpResponse ret = Resps.html(request, html);
		return ret;
	}

	@RequestPath(value = "/json")
	public HttpResponse json(HttpRequest request) throws Exception {
		HttpResponse ret = Resps.json(request, "{\"ret\":\"OK\"}");
		return ret;
	}

	@RequestPath(value = "/plain")
	public HttpResponse plain(String before, String end, HttpRequest request) throws Exception {
		String bodyString = request.getBodyString();
		HttpResponse ret = Resps.html(request, bodyString);
		return ret;
	}

	@RequestPath(value = "/post")
	public HttpResponse post(String before, String end, HttpRequest request) throws Exception {
		HttpResponse ret = Resps.html(request, "before:" + before + "<br>end:" + end);
		return ret;

	}

	@RequestPath(value = "/putsession")
	public HttpResponse putsession(String value, HttpRequest request) throws Exception {
		request.getHttpSession().setAttribute("test", value, request.getHttpConfig());
		HttpResponse ret = Resps.json(request, "设置成功:" + value);
		return ret;
	}
