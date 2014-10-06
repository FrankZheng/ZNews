package com.xzheng.znews.signal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This factory is intended to be used in order to create new Signal instances.
 * 
 * @see com.adobe.dps.viewer.signal.SignalFactory#createSignal()
 * @author nathanp
 */
@Singleton
public class SignalFactory {

	/**
	 * /**
	 * The Signal class which will be instantiated on the class that will be dispatching the
	 * signals. Implementers will most likely expose this class publicly via the ISignal interface.
	 * 
	 * @author nathanp
	 * @param <T>
	 *            The data type that will be dispatched with the Signal
	 */
	public static class SignalImpl<T> implements Signal<T> {

		/**
		 * WeakHashMap which maps handlers to a Boolean which indicates whether they were added with
		 * the addOnce method or not. This is later used to remove handlers that were added with
		 * addOnce when they are queued for dispatch.
		 */
		private WeakHashMap<Signal.Handler<T>, Boolean> _listeners =
				new WeakHashMap<Signal.Handler<T>, Boolean>();

		/**
		 * Create a new signal.
		 */
		private SignalImpl() {
		}

		/**
		 * Method used to add a new signal handler to this signal. If provided with a handler that
		 * was previously added to this Signal the handler will only be called a single time when
		 * this Signal is dispatched. This method is thread-safe.
		 * 
		 * @param handler
		 *            The handler that will receive the signal when it is dispatched
		 */
		@Override
		public void add(Signal.Handler<T> handler) {
			synchronized (this) {
				_listeners.put(handler, false);
			}
		}

		/**
		 * Method used to add a new signal handler to this signal. Unlike the add method, the
		 * handler that is passed to this method will only ever be called once. After it is called
		 * it will be removed from the Signal. If provided with a handler that was previously added
		 * to this Signal the handler will only be called a single time when this Signal is
		 * dispatched and will be removed afterwards. This method is thread-safe.
		 * 
		 * @param handler
		 *            The handler that will receive the signal when it is dispatched
		 */
		@Override
		public void addOnce(Signal.Handler<T> handler) {
			synchronized (this) {
				_listeners.put(handler, true);
			}
		}

		/**
		 * Method used to remove a signal handler from the signal. This method is thread-safe.
		 * 
		 * @param handler
		 *            The handler that was added to the Signal
		 */
		@Override
		public void remove(Signal.Handler<T> handler) {
			synchronized (this) {
				_listeners.remove(handler);
			}
		}

		/**
		 * Method used to remove all signal handlers from the signal. This method is thread-safe.
		 */
		@Override
		public void removeAll() {
			synchronized (this) {
				_listeners.clear();
			}
		}

		/**
		 * Method used to dispatch the signal and send the provided data to all of the added
		 * handlers.
		 * Handlers will all be called on the calling thread. This method is thread-safe.
		 * 
		 * @param data
		 *            The data object that will provided to each handler when executed.
		 */
		public void dispatch(T data) {
			// First, we want to create a list of all valid Handlers to be called and make sure it
			// is a clone of the _listeners map so that any concurrent modifications to that map
			// won't affect our dispatch
			List<Signal.Handler<T>> listenersClone = new ArrayList<Signal.Handler<T>>();
			synchronized (this) {
				Set<Map.Entry<Signal.Handler<T>, Boolean>> entrySet = _listeners.entrySet();
				Iterator<Map.Entry<Signal.Handler<T>, Boolean>> it = entrySet.iterator();
				while (it.hasNext()) {
					Map.Entry<Signal.Handler<T>, Boolean> entry = it.next();
					// Add listeners to our List with a strong reference
					listenersClone.add(entry.getKey());

					// remove the listener if it was added with addOnce
					if (entry.getValue()) {
						it.remove();
					}
				}
			}

			// Dispatch all of the signals using the cloned list so modifications won't cause issues
			for (Signal.Handler<T> handler : listenersClone) {
				handler.onDispatch(data);
			}
		}

		/**
		 * @return the number of listeners that are currently listening to this Signal.
		 */
		public int getNumListeners() {
			synchronized (this) {
				return _listeners.size();
			}
		}
	}
	
	@Inject
	public SignalFactory() {
	}

	/**
	 * Factory method used for instantiating new Signals (using the SignalImpl implementation of
	 * Signal). This method may be used to instantiate any generic type of Signal.
	 * 
	 * @return A new Signal instance
	 */
	public <T> SignalImpl<T> createSignal() {
		return new SignalImpl<T>();
	}
	
}
