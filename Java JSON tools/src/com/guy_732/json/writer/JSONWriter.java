package com.guy_732.json.writer;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Map.Entry;

import com.guy_732.json.JSONArray;
import com.guy_732.json.JSONBoolean;
import com.guy_732.json.JSONInteger;
import com.guy_732.json.JSONNull;
import com.guy_732.json.JSONNumber;
import com.guy_732.json.JSONObject;
import com.guy_732.json.JSONString;
import com.guy_732.json.JSONType;
import com.guy_732.json.JSONValue;

public class JSONWriter implements Closeable
{
	private final BufferedWriter writer;
	private final ArrayDeque<JSONValue> stack = new ArrayDeque<>();
	
	/**
	 * Construct a new {@link JSONWriter}
	 * 
	 * @param writer the {@link Writer} to use
	 * 
	 * @throws NullPointerException if writer is null
	 */
	public JSONWriter(Writer writer) throws NullPointerException
	{
		this(new BufferedWriter(writer));
	}
	
	/**
	 * Construct a new {@link JSONWriter}
	 * 
	 * @param writer the {@link BufferedWriter} to use
	 * @throws NullPointerException
	 */
	public JSONWriter(BufferedWriter writer) throws NullPointerException
	{
		super();
		if (writer == null)
		{
			throw new NullPointerException("'writer' cannot be null");
		}
		
		this.writer = writer;
	}
	
	/**
	 * Construct a new {@link JSONWriter}
	 * 
	 * @param stream The {@link OutputStream} to write to
	 * 
	 * @throws NullPointerException if stream is null
	 */
	public JSONWriter(OutputStream stream) throws NullPointerException
	{
		this(new BufferedWriter(new OutputStreamWriter(stream)));
	}
	
	/**
	 * write the JSONValue to the connected {@link BufferedWriter}
	 * 
	 * @param val The value to write
	 * 
	 * @throws NullPointerException if val is null OR a JSONValue's type() method returned null
	 * @throws IOException thrown by {@link BufferedWriter}
	 * @throws JSONRecursiveObject if a value contains itself
	 */
	public void writeJSONValue(JSONValue val) throws NullPointerException, IOException, JSONRecursiveObject
	{
		if (val == null)
		{
			throw new NullPointerException("'val' cannot be null");
		}
		
		writeValue(val);
		writer.flush();
	}
	
	private void writeValue(JSONValue v) throws NullPointerException, IOException, JSONRecursiveObject
	{
		JSONType t = v.type();
		switch (t)
		{
		case JSONNull:
			assert v instanceof JSONNull : "type() returned JSONNull but the value isn't a JSONnull object";
			writer.write("null");
			break;
			
		case JSONBoolean:
			assert v instanceof JSONBoolean : "type() returned JSONBoolean but the value isn't a JSONBoolean object";
			JSONBoolean b = (JSONBoolean) v;
			if (b.getValue())
			{
				writer.write("true");
			}
			else
			{
				writer.write("false");
			}
			
			break;
			
		case JSONInteger:
			assert v instanceof JSONInteger : "type() returned JSONInteger but the value isn't a JSONInteger object";
			JSONInteger i = (JSONInteger) v;
			writer.write(Long.toString(i.getValue()));
			break;
			
		case JSONNumber:
			assert v instanceof JSONNumber : "type() returned JSONNumber but the value isn't a JSONNumber object";
			JSONNumber d = (JSONNumber) v;
			writer.write(Double.toString(d.getValue()));
			break;
			
		case JSONString:
			assert v instanceof JSONString : "type() returned JSONString but the value isn't a JSONString object";
			JSONString s = (JSONString) v;
			writeString(s.getString());
			break;
			
		case JSONArray:
			assert v instanceof JSONArray : "type() returned JSONArray but the value isn't a JSONArray object";
			writeArray((JSONArray) v);
			break;
			
		case JSONObject:
			assert v instanceof JSONObject : "type() returned JSONObject but the value isn't a JSONObject object";
			writeObject((JSONObject) v);
			break;
			
		default:
			throw new JSONUnknownType(t);
		}
	}
	
	private void writeString(String s) throws IOException
	{
		writer.write('"');
		
		for (int i = 0, max = s.length(); i < max; ++i)
		{
			char c = s.charAt(i);
			switch (c)
			{
			case '"':
				writer.write("\\\""); // >> \"
				break;
				
			case '\\':
				writer.write("\\\\"); // >> \\
				break;
				
			case '\n':
				writer.write("\\n");
				break;
				
			case '\r':
				writer.write("\\r");
				break;
				
			case '\b':
				writer.write("\\b");
				break;
				
			case '\f':
				writer.write("\\f");
				break;
				
			case '\t':
				writer.write("\\t");
				break;
				
			default:
				if (!Character.isISOControl(c))
				{
					writer.write(c);
					break;
				}
				
				writer.write("\\u");
				String hex = Integer.toHexString(c);
				while (hex.length() < 4)
				{
					hex = '0' + hex;
				}
				
				writer.write(hex);
				break;
			}
		}
		
		writer.write('"');
	}
	
	private void writeObject(JSONObject ob) throws IOException, JSONRecursiveObject
	{
		if (stack.contains(ob))
		{
			throw new JSONRecursiveObject("A JSONObject contains itself");
		}
		
		stack.push(ob);
		
		writer.write('{');
		boolean is_first = true;
		for (Entry<String, JSONValue> v : ob.getMap().entrySet())
		{
			if (!is_first)
			{
				writer.write(',');
			}
			else
			{
				is_first = false;
			}
			
			writeString(v.getKey());
			writer.write(':');
			writeValue(v.getValue());
		}
		
		writer.write('}');
		
		if (stack.pop() != ob)
		{
			throw new InternalError("The popped value from the stack AFTER writing the content of the object wasn't the object written");
		}
	}
	
	private void writeArray(JSONArray arr) throws IOException, JSONRecursiveObject
	{
		if (stack.contains(arr))
		{
			throw new JSONRecursiveObject("A JSONArray contains itself");
		}
		
		stack.push(arr);
		
		writer.write('[');
		boolean is_first = true;
		for (JSONValue v : arr.getArray())
		{
			if (!is_first)
			{
				writer.write(',');
			}
			else
			{
				is_first = false;
			}
			
			writeValue(v);
		}
		
		writer.write(']');
		
		if (stack.pop() != arr)
		{
			throw new InternalError("The popped value from the stack AFTER writing the content of the array wasn't the array written");
		}
	}

	@Override
	public void close() throws IOException
	{
		writer.close();
	}

}
