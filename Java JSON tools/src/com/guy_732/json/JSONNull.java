package com.guy_732.json;

/**
 * Represent a null in java.
 * 
 * Only 1 instance: {@link JSONNull#Null JSONNull::Null} will be used as the constructor is private.
 * 
 * @author Guy_732
 */
public final class JSONNull extends JSONValue 
{
	/**
	 * The only instance of {@link JSONNull}
	 */
	public static final JSONNull Null = new JSONNull();

	private JSONNull()
	{
		super();
	}

	@Override
	public JSONType type() 
	{
		return JSONType.JSONNull;
	}
}
