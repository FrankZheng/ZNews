
package com.xzheng.znews.signal;

/**
 * This class wraps a data and sender pair.
 * 
 * @author smattiso
 * 
 * @param <T>
 *            The type of data.
 */
public class SignalMessage<T> {
	private T _data;
	private Object _sender;

	/**
	 * Constructs a SignalMessage.
	 * 
	 * @param data
	 *            The data.
	 * @param sender
	 *            The sender.
	 */
	public SignalMessage(T data, Object sender) {
		_data = data;
		_sender = sender;
	}

	/**
	 * Gets the data.
	 * 
	 * @return The data.
	 */
	public T getData() {
		return _data;
	}

	/**
	 * Gets the sender.
	 * 
	 * @return The sender.
	 */
	public Object getSender() {
		return _sender;
	}
}
