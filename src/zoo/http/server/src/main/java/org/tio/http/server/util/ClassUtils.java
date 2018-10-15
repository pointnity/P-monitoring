package org.tio.http.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.UploadFile;

import com.xiaoleilu.hutool.util.ClassUtil;

/**
 * @author tanyaowu
 *  
 */
public class ClassUtils {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ClassUtils.class);

	public static boolean isSimpleTypeOrArray(Class<?> clazz) {
		return ClassUtil.isSimpleTypeOrArray(clazz) || clazz.isAssignableFrom(UploadFile.class);
	}

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
	public ClassUtils() {
	}
}
