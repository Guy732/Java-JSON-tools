package com.guy_732.json;

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

	@Override
	public final boolean equals(Object ob)
	{
		return this == ob;
	}
}
