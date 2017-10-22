package hu.bme.mit.swsv.ris.common.mq;

/**
 * Message Queue related exception class.
 */
public class MqException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception.
	 */
	public MqException() {
		super();
	}

	/**
	 * Constructs a new exception with a detailed message.
	 *
	 * @param message Detailed message
	 */
	public MqException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with a specified cause.
	 *
	 * @param cause Cause
	 */
	public MqException(final Throwable cause) {
		super(cause);
	}
}
