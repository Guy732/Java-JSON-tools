package com.guy_732.json;

import java.util.ArrayList;

/**
 * Represent an Array.
 * <p>
 * To perform any add/remove actions on the array, do it on the ArrayList
 * returned by {@link JSONArray#getArray() JsonArray::getArray()}.
 * <p>
 * Do not add null into the ArrayList, add {@link JSONNull#Null JSONNull::Null}
 * instead.
 *
 * @author Guy_732
 */
public final class JSONArray extends JSONValue {
	private final ArrayList<JSONValue> array;

	/**
	 * Create a new empty JSONArray
	 */
	public JSONArray() {
		this(null);
	}

	/**
	 * Create a new JSONArray from a given array
	 *
	 * @param array if null, will create a new ArrayList.
	 * @throws NullPointerException if the array contains null
	 */
	public JSONArray(ArrayList<JSONValue> array) throws NullPointerException {
		super();
		if (array == null) {
			array = new ArrayList<>();
		}

		if (array.contains(null)) {
			new NullPointerException("JSONArray cannot contain null.");
		}

		this.array = array;
	}

	@Override
	public JSONType type() {
		return JSONType.JSONArray;
	}

	/**
	 * Getter
	 *
	 * @return The ArrayList used to represent the JSONArray in Java
	 */
	public ArrayList<JSONValue> getArray() {
		return array;
	}

	/**
	 * Check
	 *
	 * @return true if the Array is valid, false otherwise (cannot contain null).
	 */
	public boolean checkArray() {
		return !array.contains(null);
	}

	public JSONArray getArray(String name) {
		return this;
	}
}
