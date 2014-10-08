
package com.xzheng.znews.signal;

/**
 * Interface used to publicly expose a signal on a class. The interface doesn't allow other classes
 * to dispatch the signal and may prevent other internal functions from being called.
 */
public interface Signal<T> {

	/**
	 * Interface for a generic signal handler. Implementers will implement the onDispatch method in
	 * order to execute code when the signal that this handler is added to is dispatched. Users
	 * should take care to maintain a reference to their handlers since Signals do not guarantee the
	 * use of strong references.
	 */
	public interface Handler<T> {
		/**
		 * Method called by Signal class when the Signal is dispatched.
		 * NOTE: This may not be called on the same thread the handler was added on.
		 * 
		 * @param data
		 *            The data that is sent by the signal.
		 */
		void onDispatch(T data);
	}

	/**
	 * Method used to add a new signal handler to this signal
	 * 
	 * @param handler
	 *            The handler that will receive the signal when it is dispatched
	 */
	void add(Handler<T> handler);

	/**
	 * Method used to add a new signal handler to this signal that should only be dispatched once.
	 * After the Signal is dispatched the first time, it is automatically removed.
	 * 
	 * @param handler
	 *            The handler that will receive the signal when it is dispatched
	 */
	void addOnce(Handler<T> handler);

	/**
	 * Method used to remove a signal handler from the signal
	 * 
	 * @param id
	 *            Value that was returned by the add method
	 */
	void remove(Handler<T> handler);

	/**
	 * Method used to remove all signal handlers from the signal
	 */
	void removeAll();
}
