package org.tio.core.maintain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.tio.core.intf.Packet;
import org.tio.utils.lock.MapWithLock;

/**
 * @author tanyaowu
 */
public class ChannelContextMapWithLock {

	/** remoteAndChannelContext key: "ip:port" value: ChannelContext. */
	private MapWithLock<Integer, Packet> map = new MapWithLock<>(new HashMap<Integer, Packet>());

	/**
	 * Gets the cacheMap.
	 *
	 * @return the cacheMap
	 */
	public MapWithLock<Integer, Packet> getMap() {
		return map;
	}

	/**
	 * Adds the.
	 *
	 * @param channelContext the channel context
	 */
	public void put(Integer synSeq, Packet packet) {
		Lock lock = map.getLock().writeLock();
		try {
			lock.lock();
			Map<Integer, Packet> m = map.getObj();
			m.put(synSeq, packet);
		} catch (Throwable e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}

	public Packet remove(Integer synSeq) {
		Lock lock = map.getLock().writeLock();
		try {
			lock.lock();
			Map<Integer, Packet> m = map.getObj();
			Packet packet = m.remove(synSeq);
			return packet;
