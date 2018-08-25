package org.tio.websocket.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tanyaowu
 * 
 */
public enum Opcode {

	TEXT((byte) 1), BINARY((byte) 2), CLOSE((byte) 8), PING((byte) 9), PONG((byte) 10);

	private static final Map<Byte, Opcode> map = new HashMap<>();

	static {
		for (Opcode command : values()) {
			map.put(command.getCode(), command);
		}
	}

	public static Opcode valueOf(byte code) {
		return map.get(code);
	}
