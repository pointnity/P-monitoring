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
	//	private static final String STATIC_RES_CACHENAME = "TIO_HTTP_STATIC_RES";

	/**
	 * CacheName of static resources
	 * key:   path For example, "/index.html
	 * value: FileCache
	 */
	private static final String STATIC_RES_CONTENT_CACHENAME = "TIO_HTTP_STATIC_RES_CONTENT";

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public static void main(String[] args) {
	}

	protected HttpConfig httpConfig;

	protected Routes routes = null;

	//	private LoadingCache<String, HttpSession> loadingCache = null;

	private HttpServerInterceptor httpServerInterceptor;

	private HttpSessionListener httpSessionListener;

	private SessionCookieDecorator sessionCookieDecorator;

	private IpPathAccessStats ipPathAccessStats;

	private GuavaCache staticResCache;

	private String contextPath;
	private int contextPathLength = 0;
	private String suffix;
	private int suffixLength = 0;

	/**
	 * Temporary support for Freemarker, primarily for front-end development in the development environment, temporarily not focused as a tio-http-server feature
	 */
	private FreemarkerConfig freemarkerConfig;

	//	private static String randomCookieValue() {
	//		return RandomUtil.randomUUID();
	//	}

	/**
	 *
	 * @param httpConfig
	 * @param routes
	 * @author tanyaowu
	 */
	public DefaultHttpRequestHandler(HttpConfig httpConfig, Routes routes) {
		if (httpConfig == null) {
			throw new RuntimeException("httpConfig can not be null");
		}
		this.contextPath = httpConfig.getContextPath();
		this.suffix = httpConfig.getSuffix();

		if (StringUtils.isNotBlank(contextPath)) {
			contextPathLength = contextPath.length();
		}
		if (StringUtils.isNotBlank(suffix)) {
			suffixLength = suffix.length();
		}

		this.httpConfig = httpConfig;

		if (httpConfig.getMaxLiveTimeOfStaticRes() > 0) {
			staticResCache = GuavaCache.register(STATIC_RES_CONTENT_CACHENAME, (long) httpConfig.getMaxLiveTimeOfStaticRes(), null);
		}

		this.routes = routes;
	}

	/**
	 * Create HttpSession
	 * @return
	 * @author tanyaowu
	 */
	private HttpSession createSession(HttpRequest request) {
		String sessionId = httpConfig.getSessionIdGenerator().sessionId(httpConfig, request);
		HttpSession httpSession = new HttpSession(sessionId);
		if (httpSessionListener != null) {
			httpSessionListener.doAfterCreated(httpSession, httpConfig);
		}
		return httpSession;
	}

	/**
	 * @return the httpConfig
	 */
	public HttpConfig getHttpConfig() {
		return httpConfig;
	}

	public HttpServerInterceptor getHttpServerInterceptor() {
		return httpServerInterceptor;
	}

	public static Cookie getSessionCookie(HttpRequest request, HttpConfig httpConfig) {
		Cookie sessionCookie = request.getCookie(httpConfig.getSessionCookieName());
		return sessionCookie;
	}

	/**
	 * @return the staticResCache
	 */
	public GuavaCache getStaticResCache() {
		return staticResCache;
	}

	/**
	 *Check whether the domain name can access the site
	 * @param request
	 * @return
	 * @author tanyaowu
	 */
	private boolean checkDomain(HttpRequest request) {
		String[] allowDomains = httpConfig.getAllowDomains();
		if (allowDomains == null || allowDomains.length == 0) {
			return true;
		}
		String host = request.getHost();
		if (ArrayUtil.contains(allowDomains, host)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @author tanyaowu
	 */
	private static void gzip(HttpRequest request, HttpResponse response) {
		if (response == null) {
			return;
		}
		
		if (request.getIsSupportGzip()) {
			byte[] bs = response.getBody();
			if (bs != null && bs.length >= 600) {
				byte[] bs2 = ZipUtil.gzip(bs);
				if (bs2.length < bs.length) {
					response.setBody(bs2, request);
					response.addHeader(HttpConst.ResponseHeaderKey.Content_Encoding, "gzip");
				}
			}
		} else {
			log.info("{} Doesn't even support it.gzip, {}", request.getChannelContext(), request.getHeader(HttpConst.RequestHeaderKey.User_Agent));
		}
	}

	@Override
	public HttpResponse handler(HttpRequest request) throws Exception {
		if (!checkDomain(request)) {
			Aio.remove(request.getChannelContext(), "Come over the domain name ["+ Request.GetDomain () + "] No");
			return null;
		}

		HttpResponse ret = null;
		RequestLine requestLine = request.getRequestLine();
		String path = requestLine.getPath();

		if (StringUtils.isNotBlank(contextPath)) {
			if (StringUtils.startsWith(path, contextPath)) {
				path = StringUtils.substring(path, contextPathLength);
			} else {
				//				Aio.remove(request.getChannelContext(), "The request path is illegal and must begin with "+ ContextPath +": "+ requestline.GetLine ());
				//				return null;
			}
		}

		if (StringUtils.isNotBlank(suffix)) {
			if (StringUtils.endsWith(path, suffix)) {
				path = StringUtils.substring(path, 0, path.length() - suffixLength);
			} else {
				//				Aio.remove(request.getChannelContext(), "The request path is illegal and must end with "+ suffix +"：" + requestLine.getLine());
				//				return null;
			}
		}
		requestLine.setPath(path);

		if (ipPathAccessStats != null) {
			String ip = IpUtils.getRealIp(request);
			List<Long> list = ipPathAccessStats.durationList;

			Cookie cookie = getSessionCookie(request, httpConfig);

			for (Long duration : list) {
				IpAccessStat ipAccessStat = ipPathAccessStats.get(duration, ip);//.get(duration, ip, path);//.get(v, channelContext.getClientNode().getIp());

				ipAccessStat.count.incrementAndGet();
				ipAccessStat.setLastAccessTime(SystemTimer.currentTimeMillis());

				IpPathAccessStat ipPathAccessStat = ipAccessStat.get(path);
				ipPathAccessStat.count.incrementAndGet();
				ipPathAccessStat.setLastAccessTime(SystemTimer.currentTimeMillis());

				if (cookie == null) {
					ipAccessStat.noSessionCount.incrementAndGet();
					ipPathAccessStat.noSessionCount.incrementAndGet();
				} else {
					ipAccessStat.sessionIds.add(cookie.getValue());
				}

				IpPathAccessStatListener ipPathAccessStatListener = ipPathAccessStats.getListener(duration);
				if (ipPathAccessStatListener != null) {
					boolean isContinue = ipPathAccessStatListener.onChanged(request, ip, path, ipAccessStat, ipPathAccessStat);
					if (!isContinue) {
						return null;
					}
				}
			}
		}

		try {

			processCookieBeforeHandler(request, requestLine);
			HttpSession httpSession = request.getHttpSession();//(HttpSession) channelContext.getAttribute();

			//			GuavaCache guavaCache = GuavaCache.getCache(STATIC_RES_CACHENAME);
			//			ret = (HttpResponse) guavaCache.get(requestLine.getPath());
			//			if (ret != null) {
			//				log.info("Get response from cache: {} ", requestLine.getPath());
			//			}

			if (httpServerInterceptor != null) {
				ret = httpServerInterceptor.doBeforeHandler(request, requestLine, ret);
				if (ret != null) {
					return ret;
				}
			}
			requestLine = request.getRequestLine();
			//			if (ret != null) {
			//				return ret;
			//			}

			path = requestLine.getPath();

			Method method = null;
			if (routes != null) {
				method = routes.pathMethodMap.get(path);
			}

			if (method != null) {
				String[] paramnames = routes.methodParamnameMap.get(method);
				Class<?>[] parameterTypes = method.getParameterTypes();

				Object bean = routes.methodBeanMap.get(method);
				Object obj = null;
				Map<String, Object[]> params = request.getParams();
				if (parameterTypes == null || parameterTypes.length == 0) {
					obj = method.invoke(bean);
				} else {
					//Assign this code to refactor, first use the
					Object[] paramValues = new Object[parameterTypes.length];
					int i = 0;
					for (Class<?> paramType : parameterTypes) {
						try {
							if (paramType.isAssignableFrom(HttpRequest.class)) {
								paramValues[i] = request;
							} else if (paramType == HttpSession.class) {
								paramValues[i] = httpSession;
							} else if (paramType.isAssignableFrom(HttpConfig.class)) {
								paramValues[i] = httpConfig;
							} else if (paramType.isAssignableFrom(ChannelContext.class)) {
								paramValues[i] = request.getChannelContext();
							} else {
								if (params != null) {
									if (ClassUtils.isSimpleTypeOrArray(paramType)) {
										//										paramValues[i] = Ognl.getValue(paramnames[i], (Object) params, paramType);
										Object[] value = params.get(paramnames[i]);
										if (value != null && value.length > 0) {
											if (paramType.isArray()) {
												paramValues[i] = Convert.convert(paramType, value);
											} else {
												paramValues[i] = Convert.convert(paramType, value[0]);
											}
										}
									} else {
										paramValues[i] = paramType.newInstance();//BeanUtil.mapToBean(params, paramType, true);
										Set<Entry<String, Object[]>> set = params.entrySet();
										label2: for (Entry<String, Object[]> entry : set) {
											String fieldName = entry.getKey();
											Object[] fieldValue = entry.getValue();

											PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(paramType, fieldName, true);
											if (propertyDescriptor == null) {
												continue label2;
											} else {
												Method writeMethod = propertyDescriptor.getWriteMethod();
												if (writeMethod == null) {
													continue label2;
												}
												writeMethod = ClassUtil.setAccessible(writeMethod);
												Class<?>[] clazzes = writeMethod.getParameterTypes();
												if (clazzes == null || clazzes.length != 1) {
													log.info("The parameter length of the method is not1，{}.{}", paramType.getName(), writeMethod.getName());
													continue label2;
												}
												Class<?> clazz = clazzes[0];

												if (ClassUtils.isSimpleTypeOrArray(clazz)) {
													if (fieldValue != null && fieldValue.length > 0) {
														if (clazz.isArray()) {
															writeMethod.invoke(paramValues[i], Convert.convert(clazz, fieldValue));
														} else {
															writeMethod.invoke(paramValues[i], Convert.convert(clazz, fieldValue[0]));
														}
													}
												}
											}
										}
									}
								}
							}
						} catch (Throwable e) {
							log.error(e.toString(), e);
						} finally {
							i++;
						}
					}
					obj = method.invoke(bean, paramValues);
				}

				if (obj instanceof HttpResponse) {
					ret = (HttpResponse) obj;
					return ret;
				} else {
					if (obj == null) {
						//ret = Resps.txt(request, "");//.json(request, obj + "");
						return null;
					} else {
						ret = Resps.json(request, obj);
					}
					return ret;
				}
			} else {
				GuavaCache contentCache = null;
				FileCache fileCache = null;
				if (httpConfig.getMaxLiveTimeOfStaticRes() > 0) {
					contentCache = GuavaCache.getCache(STATIC_RES_CONTENT_CACHENAME);
					fileCache = (FileCache) contentCache.get(path);
				}
				if (fileCache != null) {
					byte[] bodyBytes = fileCache.getData();
					Map<String, String> headers = fileCache.getHeaders();
					long lastModified = fileCache.getLastModified();
					log.info("Get from cache:[{}], {}", path, bodyBytes.length);

					ret = Resps.try304(request, lastModified);
					if (ret != null) {
						ret.addHeader(HttpConst.ResponseHeaderKey.tio_from_cache, "true");

						return ret;
					}

					ret = new HttpResponse(request, httpConfig);
					ret.setBody(bodyBytes, request);
					ret.addHeaders(headers);
					return ret;
				} else {
					File pageRoot = httpConfig.getPageRoot();
					if (pageRoot != null) {
						//						String root = FileUtil.getAbsolutePath(pageRoot);
						File file = new File(pageRoot + path);
						if (!file.exists() || file.isDirectory()) {
							if (StringUtils.endsWith(path, "/")) {
								path = path + "index.html";
							} else {
								path = path + "/index.html";
							}
							file = new File(pageRoot, path);
						}

						if (file.exists()) {
							if (freemarkerConfig != null) {
								String extension = FileNameUtil.getExtension(file.getName());
								if (ArrayUtil.contains(freemarkerConfig.getSuffixes(), extension)) {
									Configuration configuration = freemarkerConfig.getConfiguration();
									Object model = freemarkerConfig.getModelMaker().maker(request);
									if (configuration != null) {
										TemplateLoader templateLoader = configuration.getTemplateLoader();//FileTemplateLoader
										if (templateLoader instanceof FileTemplateLoader) {
											try {
												String filePath = file.getCanonicalPath();
												String pageRootPath = httpConfig.getPageRoot().getCanonicalPath();
												String template = StringUtils.substring(filePath, pageRootPath.length());
												String retStr = FreemarkerUtils.generateStringByFile(template, configuration, model);
												ret = Resps.file(request, retStr.getBytes(configuration.getDefaultEncoding()), extension);
												return ret;
											} catch (Exception e) {
												//freemarker Compile exception of all go commonview
												log.error(e.toString());
											}
										}
									}
								}
							}

							ret = Resps.file(request, file);
