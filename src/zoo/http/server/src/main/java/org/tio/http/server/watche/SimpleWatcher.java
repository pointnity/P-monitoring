package org.tio.http.server.watche;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;

import com.xiaoleilu.hutool.io.watch.WatchMonitor;
import com.xiaoleilu.hutool.io.watch.Watcher;

/**
 *
 * @author tanyaowu
 *  
 */
public class SimpleWatcher implements Watcher {

	public static void main(String[] args) {
		WatchMonitor watchMonitor = WatchMonitor.create(Paths.get("c://"), 1000000, new WatchEvent.Kind() {

			@Override
			public String name() {
				return null;
			}

			@Override
			public Class<?> type() {
				return null;
			}
		});
		SimpleWatcher simpleWatcher = new SimpleWatcher();
