package hu.bme.mit.swsv.ris.tsm;

import hu.bme.mit.swsv.ris.common.mq.MqException;
import hu.bme.mit.swsv.ris.signals.AbstractSignal;

/**
 * Interface for the message queue (MQ) client, which can send and receive
 * signals.
 */
public interface MqClient {

	/**
	 * Publishes a signal.
	 *
	 * @param signal Signal to be published
	 */
	void publish(AbstractSignal signal);

	/**
	 * Disconnects the client from the service.
	 *
	 * @throws MqException An exception is thrown when the attempt to disconnect
	 *             fails
	 */
	void disconnect() throws MqException;
}
