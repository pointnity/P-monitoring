package org.tio.http.server.handler;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.Cookie;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpConst;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.http.common.RequestLine;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.http.common.session.HttpSession;
import org.tio.http.common.utils.IpUtils;
import org.tio.http.server.intf.HttpServerInterceptor;
import org.tio.http.server.intf.HttpSessionListener;
import org.tio.http.server.mvc.Routes;
import org.tio.http.server.session.SessionCookieDecorator;
import org.tio.http.server.stat.ip.path.IpAccessStat;
import org.tio.http.server.stat.ip.path.IpPathAccessStat;
import org.tio.http.server.stat.ip.path.IpPathAccessStatListener;
import org.tio.http.server.stat.ip.path.IpPathAccessStats;
import org.tio.http.server.util.ClassUtils;
import org.tio.http.server.util.Resps;
import org.tio.http.server.view.freemarker.FreemarkerConfig;
import org.tio.utils.SystemTimer;
import org.tio.utils.cache.guava.GuavaCache;
import org.tio.utils.freemarker.FreemarkerUtils;

import com.xiaoleilu.hutool.bean.BeanUtil;
import com.xiaoleilu.hutool.convert.Convert;
import com.xiaoleilu.hutool.util.ArrayUtil;
import com.xiaoleilu.hutool.util.ClassUtil;
import com.xiaoleilu.hutool.util.ZipUtil;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import jodd.io.FileNameUtil;

/**
 *
 * @author tanyaowu
 *
 */
public class DefaultHttpRequestHandler implements HttpRequestHandler {
	private static Logger log = LoggerFactory.getLogger(DefaultHttpRequestHandler.class);

	//	/**
	//	 * CacheName of static resources
	//	 * key:   path For example, "/index.html
	//	 * value: HttpResponse
	//	 */
