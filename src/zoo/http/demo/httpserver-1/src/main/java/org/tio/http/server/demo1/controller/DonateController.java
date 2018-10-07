package org.tio.http.server.demo1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.demo1.model.Donate;
import org.tio.http.server.demo1.service.DonateService;
import org.tio.http.server.util.Resps;

import com.jfinal.plugin.activerecord.Page;

/**
 * @author tanyaowu
 *  
 */
@RequestPath(value = "/donate")
public class DonateController {
	private static Logger log = LoggerFactory.getLogger(DonateController.class);

	static final DonateService srv = DonateService.me;

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	public DonateController() {
	}

	@RequestPath(value = "/page")
	public HttpResponse page(Integer pageNumber, Integer pageSize, HttpRequest request) throws Exception {
		Page<Donate> page = srv.page(pageNumber, pageSize);
		HttpResponse ret = Resps.json(request, page);
		return ret;
	}
}
