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
