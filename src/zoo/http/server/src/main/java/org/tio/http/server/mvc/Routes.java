package org.tio.http.server.mvc;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.annotation.RequestPath;
import org.tio.utils.json.Json;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import com.xiaoleilu.hutool.util.ArrayUtil;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.ClassAnnotationMatchProcessor;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.MethodAnnotationMatchProcessor;

/**
 * @author tanyaowu
 *  
 */
public class Routes {
	private static Logger log = LoggerFactory.getLogger(Routes.class);
	//	private HttpConfig httpConfig = null;

	//	private String[] scanPackages = null;

	

	/**
	 * Format a path such as "/user", "/"
	 * @param initPath
	 * @return
	 * @author tanyaowu
	 */
	private static String formateBeanPath(String initPath) {
		//		if (StringUtils.isBlank(initPath)) {
		//			return "/";
		//		}
		//		initPath = StringUtils.replaceAll(initPath, "//", "/");
		//		if (!StringUtils.startsWith(initPath, "/")) {
		//			initPath = "/" + initPath;
		//		}
		//
		//		if (StringUtils.endsWith(initPath, "/")) {
		//			initPath = initPath.substring(0, initPath.length() - 1);
		//		}
		return initPath;
	}

	private static String formateMethodPath(String initPath) {
		//		if (StringUtils.isBlank(initPath)) {
		//			return "";
		//		}
		//		initPath = StringUtils.replaceAll(initPath, "//", "/");
		//		if (!StringUtils.startsWith(initPath, "/")) {
		//			initPath = "/" + initPath;
		//		}

		return initPath;
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
