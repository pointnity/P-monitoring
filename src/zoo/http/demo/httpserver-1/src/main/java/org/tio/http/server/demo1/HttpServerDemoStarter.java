package org.tio.http.server.demo1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.demo1.init.HttpServerInit;
import org.tio.http.server.demo1.init.JfinalInit;
import org.tio.http.server.demo1.init.JsonInit;
import org.tio.http.server.demo1.init.PropInit;

/**
 * ab -c 10 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 20 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 40 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 60 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 80 -n 200000 -k http://127.0.0.1:9527/test/abtest
