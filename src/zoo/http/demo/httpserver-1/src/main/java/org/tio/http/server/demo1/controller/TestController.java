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
