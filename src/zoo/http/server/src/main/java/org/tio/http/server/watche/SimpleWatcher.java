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
		watchMonitor.setWatcher(simpleWatcher);
		watchMonitor.start();
	}

	@Override
	public void onCreate(WatchEvent<?> event, Path currentPath) {
		Object obj = event.context();
		System.out.println("Create：" + obj);

	}

	@Override
	public void onDelete(WatchEvent<?> event, Path currentPath) {
		Object obj = event.context();
		System.out.println(The Delete：" + obj);
	}

	@Override
	public void onModify(WatchEvent<?> event, Path currentPath) {
		Object obj = event.context();
		System.out.println("Modify：" + obj);
	}

	@Override
	public void onOverflow(WatchEvent<?> event, Path currentPath) {
		Object obj = event.context();
		System.out.println("Overflow：" + obj);
	}
}
