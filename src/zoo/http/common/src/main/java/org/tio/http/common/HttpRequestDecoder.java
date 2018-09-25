package org.http.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.core.ChannelContext;
import org.core.exception.AioDecodeException;
import org.core.exception.LengthOverflowException;
import org.core.utils.ByteBufferUtils;
import org.http.common.HttpConst.RequestBodyFormat;
import org.http.common.utils.HttpParseUtils;

import com.xiaoleilu.hutool.util.StrUtil;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpRequestDecoder {
	public static enum Step {
		firstline, header, body
	}

	private static Logger log = LoggerFactory.getLogger(HttpRequestDecoder.class);

	/**
