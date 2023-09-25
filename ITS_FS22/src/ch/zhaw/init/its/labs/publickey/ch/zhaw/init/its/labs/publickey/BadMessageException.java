package ch.zhaw.init.its.labs.publickey;

/** An exception signalling something is wrong with the RSA input.
 * 
 * @author neut
 *
 */
public class BadMessageException extends Exception {
	private static final long serialVersionUID = -27474032478390563L;

	/** Generates a BadMessageException from a given message.
	 * 
	 * @param message the message to put into this BadMessageException
	 */
	public BadMessageException(String message) {
		super(message);
	}

	/** Turns a Throwable into a BadMessageException
	 * 
	 * @param cause the (root) cause for this exception
	 */
	public BadMessageException(Throwable cause) {
		super(cause);
	}

	/** Turns a Throwable and message into a BadMessageException.
	 * 
	 * @param message the message to put into this BadMessageException
	 * @param cause the (root) cause for this exception
	 */
	public BadMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	/** Turns a Throwable and other information into a BadMessageException
	 * 
	 * @param message the message to put into this BadMessageException
	 * @param cause the (root) cause for this exception
	 * @param enableSuppression whether to suppress this exception
	 * @param writableStackTrace whether the stack trace is writable
	 */
	public BadMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
