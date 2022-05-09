package com.guy_732.json.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Reading JSON data from a Reader object
 * 
 * @author Guy_732
 */
public class ReaderJSONReader extends JSONReader
{
	@Override
	public void close() throws IOException
	{
		reader.close();
	}

	public static final int DEFAULT_BUFFER_SIZE = 512;
	public static final int MINIMUM_BUFFER_SIZE = 128;

	private final Reader reader;
	private final char buffer[];

	private int end = 0;
	private int position = 0;

	private boolean firstCharRead = false;

	public ReaderJSONReader(Reader reader, int buffer_size) throws IllegalArgumentException
	{
		super();

		if (reader == null)
		{
			throw new NullPointerException("'reader' cannot be null");
		}

		if (buffer_size < MINIMUM_BUFFER_SIZE)
		{
			throw new IllegalArgumentException("'buffer_size' must be >= MINIMUM_BUFFER_SIZE");
		}

		this.reader = reader;
		this.buffer = new char[buffer_size];
	}

	public ReaderJSONReader(Reader reader)
	{
		this(reader, DEFAULT_BUFFER_SIZE);
	}

	public ReaderJSONReader(InputStream stream)
	{
		this(new InputStreamReader(stream));
	}

	public ReaderJSONReader(InputStream stream, int buffer_size)
	{
		this(new InputStreamReader(stream), buffer_size);
	}

	@Override
	public boolean makeAvailable(int minimum) throws IOException
	{
		if (buffer.length < minimum)
		{
			throw new IllegalArgumentException(
					String.format("Cannot fill buffer of length %2$d with %1$d characters.", minimum, buffer.length));
		}

		if (minimum <= end - position)
		{
			return true;
		}

		return fillBuffer(minimum);
	}

	@Override
	public int getAvailable()
	{
		return end - position;
	}

	@Override
	public char nextCharacter()
	{
		return buffer[position++];
	}

	@Override
	public char peekCharacter(int offset)
	{
		return buffer[position + offset];
	}

	/**
	 * Function used by {@link ReaderJSONReader#makeAvailable(int)
	 * ReaderJSONReader::makeAvailable(int)}
	 * 
	 * @param amount Number of character needed in the buffer, delete consumed
	 *               characters.
	 * @return boolean, success
	 * 
	 * @throws IOException Thrown by {@link Reader#read(char[], int, int)
	 *                     Reader::read(char[], int, int)}
	 */
	private boolean fillBuffer(int amount) throws IOException
	{
		if (0 != position && 0 != end)
		{
			System.arraycopy(buffer, position, buffer, 0, getAvailable());
			end -= position; // because we moved the content << by `position` elements
			position = 0;
		}

		while (getAvailable() < amount)
		{
			int read = reader.read(buffer, end, buffer.length - end);
			if (-1 == read) // EOF
			{
				return false;
			}
			else
			{
				end += read;
			}
		}

		if (!firstCharRead && end >= 1)
		{
			if (buffer[0] == JSONReader.BYTE_ORDER_MARK)
			{
				position++;
			}

			firstCharRead = true;
		}

		return true;
	}
}
