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
