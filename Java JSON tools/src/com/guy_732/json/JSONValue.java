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
}
