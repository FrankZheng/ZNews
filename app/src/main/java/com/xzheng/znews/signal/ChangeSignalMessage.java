package com.xzheng.znews.signal;

/**
 * An extension of the SignalMessage that is designed to be used for property changes.
 * 
 * @author nathanp
 */
public class ChangeSignalMessage<T> extends SignalMessage<T> {
	private T _oldValue;

	/**
	 * Constructs a ChangeSignalMessage
	 * 
	 * @param oldValue
	 *            The previous value of the property that was changed
	 * @param newValue
	 *            The new value of the property that was changed
	 * @param sender
	 *            The object that was responsible for making the change
	 */
	public ChangeSignalMessage(T oldValue, T newValue, Object sender) {
		super(newValue, sender);
		_oldValue = oldValue;
	}

	/**
	 * @return The new value of the property that was changed
	 */
	public T getNewValue() {
		return getData();
	}

	/**
	 * @return The old value of the property that was changed
	 */
	public T getOldValue() {
		return _oldValue;
	}
}
