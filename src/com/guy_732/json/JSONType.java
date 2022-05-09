package com.guy_732.json;

/**
 * Returned by the corresponding Objects implementing {@link JSONValue} when the
 * method {@link JSONValue#type() JSONValue::type()} is called.
 * 
 * @author Guy_732
 */
public enum JSONType
{
	/**
	 * For the type {@link JSONString}
	 */
	JSONString,

	/**
	 * For the type {@link JSONArray}
	 */
	JSONArray,

	/**
	 * For the type {@link JSONObject}
	 */
	JSONObject,

	/**
	 * For the type {@link JSONNumber}
	 */
	JSONNumber,

	/**
	 * For the type {@link JSONInteger}
	 */
	JSONInteger,

	/**
	 * For the type {@link JSONNull}
	 */
	JSONNull,

	/**
	 * For the type {@link JSONBoolean}
	 */
	JSONBoolean
}
