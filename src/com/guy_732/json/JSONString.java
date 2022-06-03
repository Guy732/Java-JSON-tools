package com.guy_732.json;

/**
 * Represent a String
 *
 * @author Guy_732
 */
public final class JSONString extends JSONValue {
	private String value;

	/**
	 * Create a new JSONString storing an empty String.
	 */
	public JSONString() {
		this("");
	}

	/**
	 * Create a new JSONString storing a given String
	 *
	 * @param value The String to store.
	 * @throws NullPointerException if {@code value == null}
	 */
	public JSONString(String value) throws NullPointerException {
		super();
		if (value == null) {
			throw new NullPointerException("'value' cannot be null.");
		}

		this.value = value;
	}

	@Override
	public JSONType type() {
		return JSONType.JSONString;
	}

	/**
	 * Getter
	 *
	 * @return The String stored within the object.
	 */
	public String getString() {
		return value;
	}

	public String getString(String name) {
		return value;
	}

	/**
	 * Setter
	 *
	 * @param newVal The new String to store.
	 * @throws NullPointerException if {@code newVal == null}
	 */
	public void setString(String newVal) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("'newVal' cannot be null.");
		}

		value = newVal;
	}

}
