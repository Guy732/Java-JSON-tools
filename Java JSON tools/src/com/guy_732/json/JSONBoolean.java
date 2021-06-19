package com.guy_732.json;

/**
 * Represent a boolean
 * 
 * Only 2 instances: {@link JSONBoolean#True JSONBoolean::True} & {@link JSONBoolean#False JSONBoolean::False}
 * 
 * @author Guy_732
 */
public class JSONBoolean extends JSONValue
{
	/**
	 * One of the 2 instances, represent true.
	 */
	public static final JSONBoolean True = new JSONBoolean(true);
	
	/**
	 * One of the 2 instances, represent false.
	 */
	public static final JSONBoolean False = new JSONBoolean(false);
	
	
	private final boolean value;
	
	/**
	 * Create a new JSONBoolean storing a given boolean
	 * 
	 * @param value The boolean to store
	 */
	private JSONBoolean(boolean value)
	{
		super();
		
		this.value = value;
	}

	@Override
	public JSONType type()
	{
		return JSONType.JSONBoolean;
	}
	
	/**
	 * Getter
	 * 
	 * @return The boolean stored
	 */
	public boolean getValue()
	{
		return value;
	}
}
