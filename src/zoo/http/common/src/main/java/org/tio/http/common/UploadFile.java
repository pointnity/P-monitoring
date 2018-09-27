package org.http.common;

/**
 *
 * @author tanyaowu
 * 
 */
public class UploadFile {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	private String name = null;
	private int size = -1;

	private byte[] data = null;
	//    private File file = null;

	/**
	 *
	 */
	public UploadFile() {

	}

	public byte[] getData() {
		return data;
	}

	public String getName() {
		return name;
	}
