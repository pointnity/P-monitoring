package org.tio.core.exception;

/**
 *
 * @author tanyaowu
 *  
 */
public class AioDecodeException extends java.lang.Throwable {

	/**
	 * @Meaning:
	 * @Type: Long
	 */
	private static final long serialVersionUID = 5231789012657669073L;

	/**
	 *
	 *
	 * @author tanyaowu
	 *
	 */
	public AioDecodeException() {
	}

	/**
	 * @param message
	 *
	 * @author tanyaowu
	 *
	 */
	public AioDecodeException(String message) {
		super(message);

	}

	/**
	 * @param message
	 * @param cause
	 *
	 * @author tanyaowu
	 *
	 */
	public AioDecodeException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 *
	 * @author tanyaowu
	 *
	 */
	public AioDecodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	/**
	 * @param cause
	 *
	 * @author tanyaowu
	 *
	 */
	public AioDecodeException(Throwable cause) {
		super(cause);

	}

}
