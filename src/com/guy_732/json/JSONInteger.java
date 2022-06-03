package com.guy_732.json;

/**
 * Represent a long (NOT floating point number, see {@link JSONNumber} for
 * those)
 *
 * @author Guy_732
 */
public final class JSONInteger extends JSONValue {
	private long value;

	/**
	 * Construct a new JSONInteger storing 0
	 */
	public JSONInteger() {
		this(0);
	}

	/**
	 * Create a new JSONInteger storing a given value.
	 *
	 * @param value The value to store in the JSONInteger.
	 */
	public JSONInteger(long value) {
		super();

		this.value = value;
	}

	@Override
	public JSONType type() {
		return JSONType.JSONInteger;
	}

	/**
	 * Getter
	 *
	 * @return The value stored in the JSONInteger
	 */
	public long getValue() {
		return value;
	}

	/**
	 * Setter
	 *
	 * @param value The new value to store in the JSONInteger
	 */
	public void setValue(long value) {
		this.value = value;
	}

	public long getInteger(String name) {
		return getValue();
	}

	public double getNumber(String name) {
		return getValue();
	}

}
