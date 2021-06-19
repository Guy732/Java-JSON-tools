package com.guy_732.json.reader;

import java.io.IOException;

public class StringJSONReader extends JSONReader
{
	private final String buffer;
	private int position = 0;

	/**
	 * Create a new JSONReader relying on a String
	 * 
	 * @param s the string to use
	 * @throws NullPointerException when 's' is null
	 */
	public StringJSONReader(String s) throws NullPointerException
	{
		super();
		
		if (s == null)
		{
			throw new NullPointerException("'s' cannot be null");
		}
		
		buffer = s;
	}
	
	/**
	 * Create a new JSONReader relying on a String
	 * 
	 * @param value The char array representing the string
	 * @throws NullPointerException when 'value' is null
	 */
	public StringJSONReader(char[] value) throws NullPointerException
	{
		this(new String(value));
	}

	@Override
	public void close() throws IOException
	{}

	@Override
	public boolean makeAvailable(int minimum) throws IOException {
		return buffer.length() - position >= minimum;
	}

	@Override
	public int getAvailable()
	{
		return buffer.length() - position;
	}

	@Override
	public char nextCharacter()
	{
		return buffer.charAt(position++);
	}

	@Override
	public char peekCharacter(int offset)
	{
		return buffer.charAt(position + offset);
	}
}
