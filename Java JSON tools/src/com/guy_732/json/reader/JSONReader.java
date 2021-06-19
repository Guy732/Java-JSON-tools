package com.guy_732.json.reader;

import java.io.Closeable;
import java.io.IOException;
import com.guy_732.json.exception.JSONSynthaxException;

public abstract class JSONReader implements Closeable
{
	/**
	 * A {@code char} that represents the Unicode byte order mark. A leading
	 * byte order mark in a character sequence should be ignored by a
	 * {@link JSONReader}.
	 */
	public static final char BYTE_ORDER_MARK = '\uFEFF';
	
	
	/**
	 * If this function returns true, {@link getAvailable()} should return minimum or a value greater than minimum.
	 * Ensures that a given minimum of characters is available to be consumed.
	 * 
	 * @param minimum The minimum amount of character in the buffer.
	 * 
	 * @return Whether the desired amount of available characters could be
	 *         ensured or the end of the character sequence has been reached.
	 */
	public abstract boolean makeAvailable(int minimum) throws IOException;
	
	/**
	 * Returns the amount of currently available characters.
	 * 
	 * @return The amount of currently available characters.
	 */
	public abstract int getAvailable();
	
	/**
	 * Consumes and returns the next character in the character sequence.
	 * 
	 * @return The next character in the character sequence. Must be
	 *         non-negative.
	 */
	public abstract char nextCharacter();
	
	/**
	 * Returns a future character in the character sequence, without consuming
	 * it.
	 * 
	 * <p>
	 * Callers must {@link JSONReader#makeAvailable(int) ensure}, that the
	 * desired amount of characters is available.
	 * 
	 * @param offset
	 *            The amount of characters to look ahead. Must be non-negative.
	 * 
	 * @return The future character in the character sequence.
	 */
	public abstract char peekCharacter(int offset);

	/**
	 * Consumes and returns the next characters in the character sequence.
	 * 
	 * <p>
	 * Callers must {@link JSONReader#makeAvailable(int) ensure}, that the
	 * desired amount of characters is available.
	 * 
	 * @param length
	 *            The amount of characters to be consumed. Must be non-negative.
	 * 
	 * @return The next characters in the character sequence.
	 */
	public String nextString(int length)
	{
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; ++i)
		{
			s.append(nextCharacter());
		}
		
		return s.toString();
	}
	
	/**
	 * Consumes and appends the next characters in the character sequence to the
	 * given {@link StringBuilder}.
	 * 
	 * <p>
	 * Callers must {@link JSONReader#makeAvailable(int) ensure}, that the
	 * desired amount of characters is available.
	 * 
	 * @param builder
	 *            The string builder to add the characters to. Must be
	 *            non-negative.
	 * 
	 * @param length
	 *            The amount of characters to be consumed.
	 */
	public void appendNextString(StringBuilder builder, int length)
	{
		builder.append(nextString(length));
	}
	
	/**
	 * Returns the next non whitespace (' ', '\n', '\r', '\t') character
	 * 
	 * @param err The error thown if there are no whitespace character.
	 * @return The next non whitespace character
	 * 
	 * @throws IOException Thrown by {@link JSONReader#makeAvailable(int) JSONReader::makeAvailable(int)}
	 * @throws JSONSynthaxException Thrown if no characters are returned, use `err`
	 */
	public char nextNonWhitespace(JSONSynthaxError err) throws IOException, JSONSynthaxException
	{
		while (makeAvailable(1))
		{
			for (int i = 0, n = getAvailable(); i < n; ++i)
			{
				char c = nextCharacter();
				if (!Character.isWhitespace(c))
				{
					return c;
				}
			}
		}
		
		throw new JSONSynthaxException(err);
	}
}
