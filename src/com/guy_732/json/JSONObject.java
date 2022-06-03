package com.guy_732.json;

import java.util.HashMap;

/**
 * Represent a JSONObject (maps {@link String Strings} to other {@link JSONValue
 * JSONValues})
 *
 * @author Guy_732
 */
public final class JSONObject extends JSONValue {
	private final HashMap<String, JSONValue> ob;

	/**
	 * Construct a new JSONObject
	 */
	public JSONObject() {
		this(null);
	}

	/**
	 * Construct a new JSONObject from an already existing HashMap.
	 *
	 * @param ob The HashMap, if null, will create a new one.
	 * @throws NullPointerException when 1 key or 1 value in ob is null.
	 */
	public JSONObject(HashMap<String, JSONValue> ob) throws NullPointerException {
		super();

		if (ob == null) {
			this.ob = new HashMap<>();
		} else {
			if (ob.containsKey(null)) {
				throw new NullPointerException("No key can be null.");
			}

			if (ob.containsValue(null)) {
				throw new NullPointerException("No value can be null, null values should be the instance of JSONNull.");
			}

			this.ob = ob;
		}
	}

	@Override
	public JSONType type() {
		return JSONType.JSONObject;
	}

	/**
	 * Getter
	 *
	 * @return The HashMap used to represent the JSONObject inside Java.
	 */
	public HashMap<String, JSONValue> getMap() {
		return ob;
	}

	/**
	 * Remove a value from the JSONObject
	 *
	 * @param key The key to remove from the JSONObject.
	 * @return The JSONValue previously associated with the key (null if the key
	 * didn't exist).
	 */
	public JSONValue removeKey(String key) {
		return ob.remove(key);
	}

	/**
	 * Add a key, value pair to the object. Will NOT add the pair if the key is
	 * already in the JSONObject.
	 *
	 * @param key   The key under which the value will be stored
	 * @param value The value associated with the key.
	 * @return true if the value was added, false otherwise (value already existed).
	 * @throws NullPointerException when {@code key == null || value == null}
	 */
	public boolean addValue(String key, JSONValue value) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException("'key' cannot be null.");
		}

		if (value == null) {
			throw new NullPointerException("'value' cannot be null.");
		}

		if (ob.containsKey(key)) {
			return false;
		}

		ob.put(key, value);
		return true;
	}

	/**
	 * Fetch a value inside the JSONObject
	 *
	 * @param key The key under which the value is stored.
	 * @return The JSONValue associated with the key (null if the key does not exist
	 * in the object).
	 * @throws NullPointerException when {@code key == null}
	 */
	public JSONValue getJSONValue(String key) throws NullPointerException {
		if (key == null) {
			throw new NullPointerException("'key' cannot be null.");
		}

		return ob.get(key);
	}

	/**
	 * Check
	 *
	 * @return true if the Object is valid, false otherwise (cannot contain null:
	 * key/value)
	 */
	public boolean checkObject() {
		if (ob.containsKey(null)) {
			return false;
		}

		return !ob.containsValue(null);
	}

	public JSONObject getObject(String name) {
		return this;
	}
}
