package com.guy_732.json;

import java.util.ArrayDeque;

/**
 * class extended by all JSONValues (all listed in the enumeration
 * {@link JSONType})
 * 
 * @author Guy_732
 */
public abstract class JSONValue
{
	/**
	 * Note: you can also use {@code isinstance} however with this method, you can
	 * use switch case statements.
	 * 
	 * @return The {@link JSONType} of the JSONValue
	 */
	public abstract JSONType type();

	/**
	 * Do not use to compare 2 {@link JSONValue JSONValues}, this will only return
	 * {@code this == ob} and won't actually compare the content.
	 * 
	 * This function is here for the JSONWriter to be sure that it doesn't get stuck
	 * in an infinite loop trying to write object containing themselves as
	 * {@link ArrayDeque#contains(Object) ArrayDeque::contains(Object)} will call
	 * this method.
	 * 
	 * @return {@code this == ob}
	 */
	@Override
	public final boolean equals(Object ob)
	{
		return this == ob;
	}

	/**
	 * Method overriden by {@link JSONInteger} and returning it's value, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONInteger})
	 * 
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONInteger}, no return otherwise
	 */
	public long getInteger(String name)
	{
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" wasn't a JSONInteger"));
	}

	/**
	 * Method overriden by {@link JSONInteger} and {@link JSONNumber} and returning
	 * it's value, the method throw an {@link IllegalArgumentException} if it isn't
	 * overriden (not a {@link JSONInteger} nor {@link JSONNumber}
	 * 
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONInteger} or {@link JSONNumber} no return
	 *         otherwise
	 */
	public double getNumber(String name)
	{
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONInteger nor a JSONNumber"));
	}

	/**
	 * Method overriden by {@link JSONString} and returning it's value, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONString})
	 * 
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONString}, no return otherwise
	 */
	public String getString(String name)
	{
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONString"));
	}

	/**
	 * Method overriden by {@link JSONBoolean} and returning it's value, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONBoolean})
	 * 
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONBoolean}, no return otherwise
	 */
	public boolean getBoolean(String name)
	{
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONBoolean"));
	}
	
	/**
	 * Method overriden by {@link JSONArray} and returning itself, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONArray})
	 * 
	 * @param name An optional name to throw with the exception message
	 * @return the {@link JSONArray}, no return otherwise
	 */
	public JSONArray getArray(String name)
	{
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONArray"));
	}
	
	/**
	 * Method overriden by {@link JSONObject} and returning itself, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONObject})
	 * 
	 * @param name An optional name to throw with the exception message
	 * @return the {@link JSONObject}, no return otherwise
	 */
	public JSONObject getObject(String name)
	{
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONArray"));
	}
}
