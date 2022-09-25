package com.guy_732.json;

import com.guy_732.json.reader.JSONParser;
import com.guy_732.json.writer.JSONWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayDeque;

/**
 * class extended by all JSONValues (all listed in the enumeration
 * {@link JSONType})
 *
 * @author Guy_732
 */
public abstract class JSONValue {
	/**
	 * Note: you can also use {@code instanceof} however with this method, you can
	 * use switch case statements.
	 *
	 * @return The {@link JSONType} of the JSONValue
	 */
	public abstract JSONType type();

	/**
	 * Do not use to compare 2 {@link JSONValue JSONValues}, this will only return
	 * {@code this == ob} and won't actually compare the content.
	 * <p>
	 * This function is here for the JSONWriter to be sure that it doesn't get stuck
	 * in an infinite loop trying to write object containing themselves as
	 * {@link ArrayDeque#contains(Object) ArrayDeque::contains(Object)} will call
	 * this method.
	 *
	 * @return {@code this == ob}
	 */
	@Override
	public final boolean equals(Object ob) {
		return this == ob;
	}

	@Override
	public final String toString() {
		StringWriter out = new StringWriter();
		try (JSONWriter writer = new JSONWriter(out)) {
			writer.writeJSONValue(this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return out.toString();
	}

	/**
	 * Method overriden by {@link JSONInteger} and returning its value, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONInteger})
	 *
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONInteger}, no return otherwise
	 */
	public long getInteger(String name) {
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" wasn't a JSONInteger"));
	}

	/**
	 * Method overriden by {@link JSONInteger} and {@link JSONNumber} and returning
	 * its value, the method throw an {@link IllegalArgumentException} if it isn't
	 * overriden (not a {@link JSONInteger} nor {@link JSONNumber}
	 *
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONInteger} or {@link JSONNumber} no return
	 * otherwise
	 */
	public double getNumber(String name) {
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONInteger nor a JSONNumber"));
	}

	/**
	 * Method overriden by {@link JSONString} and returning its value, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONString})
	 *
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONString}, no return otherwise
	 */
	public String getString(String name) {
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONString"));
	}

	/**
	 * Method overriden by {@link JSONBoolean} and returning its value, the method
	 * throw an {@link IllegalArgumentException} if it isn't overriden (not a
	 * {@link JSONBoolean})
	 *
	 * @param name An optional name to throw with the exception message
	 * @return the value of the {@link JSONBoolean}, no return otherwise
	 */
	public boolean getBoolean(String name) {
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
	public JSONArray getArray(String name) {
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
	public JSONObject getObject(String name) {
		name = (name != null ? name : "value");
		throw new IllegalArgumentException(name.concat(" was not a JSONArray"));
	}

	public static JSONValue valueOf(String s) {
		return ((s == null) ? JSONNull.Null : new JSONString(s));
	}

	public static JSONInteger valueOf(long l) {
		return new JSONInteger(l);
	}

	public static JSONNumber valueOf(double d) {
		return new JSONNumber(d);
	}

	public static JSONBoolean valueOf(boolean b) {
		return (b ? JSONBoolean.True : JSONBoolean.False);
	}

	public static JSONValue parseString(String jsonString) {
		try (JSONParser parser = new JSONParser(jsonString)) {
			return parser.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
