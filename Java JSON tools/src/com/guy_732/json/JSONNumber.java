package com.guy_732.json;

/**
 * Represent a double (floating point values, for integers see
 * {@link JSONInteger})
 * 
 * @author Guy_732
 */
public final class JSONNumber extends JSONValue
{
	private double value;

	/**
	 * Create a new JSONNumber storing 0.0
	 */
	public JSONNumber()
	{
		this(0);
	}

	/**
	 * Create a new JSONNumber storing a given value.
	 * 
	 * @param value The value to store in the JSONNumber.
	 */
	public JSONNumber(double value)
	{
		super();

		this.value = value;
	}

	@Override
	public JSONType type()
	{
		return JSONType.JSONNumber;
	}

	/**
	 * Getter
	 * 
	 * @return The value stored in the JSONNumber
	 */
	public double getValue()
	{
		return value;
	}

	/**
	 * Setter
	 * 
	 * @param value The new value to store in the JSONNumber
	 */
	public void setValue(double value)
	{
		this.value = value;
	}
}
