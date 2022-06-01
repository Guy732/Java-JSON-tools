package com.guy_732.json.reader;

import com.guy_732.json.*;
import com.guy_732.json.exception.JSONSyntaxException;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;

/**
 * This class parse JSON data with the method {@link JSONParser#parse()
 * JSONParser::parse()} and returns a {@link JSONValue}
 *
 * @author Guy_732
 */
public class JSONParser implements Closeable {
	private final JSONReader reader;

	private final StringBuilder local_builder = new StringBuilder();

	private boolean closed = false;

	/**
	 * Construct a new {@link JSONParser}
	 *
	 * @param reader The reader reading the JSON content
	 * @throws NullPointerException if reader is null
	 */
	public JSONParser(JSONReader reader) throws NullPointerException {
		super();

		if (reader == null) {
			throw new NullPointerException("'reader' cannot be null");
		}

		this.reader = reader;
	}

	/**
	 * Construct a new {@link JSONParser}
	 *
	 * @param reader The {@link Reader} to read from.
	 * @throws NullPointerException If the given {@link Reader} is null
	 */
	public JSONParser(Reader reader) throws NullPointerException {
		this(new ReaderJSONReader(reader));
	}

	/**
	 * Construct a new {@link JSONParser}
	 *
	 * @param stream The stream to read from
	 * @throws NullPointerException if stream is null
	 */
	public JSONParser(InputStream stream) throws NullPointerException {
		this(new ReaderJSONReader(stream));
	}

	/**
	 * Creates a new {@link JSONParser}
	 *
	 * @param s The {@link String} storing the JSON data.
	 * @throws NullPointerException If 's' is null
	 */
	public JSONParser(String s) throws NullPointerException {
		this(new StringJSONReader(s));
	}

	/**
	 * Creates a new {@link JSONParser}
	 *
	 * @param value The char[] storing the JSON data
	 * @throws NullPointerException If 'value' is null
	 */
	public JSONParser(char[] value) throws NullPointerException {
		this(new StringJSONReader(value));
	}

	private JSONValue parseNextValue(char c) throws JSONSyntaxException, IOException {
		switch (c) {
			case '{':
				return parseObject();

			case '[':
				return parseArray();

			case '"':
				return parseNextString();

			default:
				return parseNextLiteral(c);

		}
	}

	private JSONArray parseArray() throws JSONSyntaxException, IOException {
		ArrayList<JSONValue> array = new ArrayList<>();
		boolean array_end = false;
		boolean is_empty = true;

		JSONSynthaxError err = JSONSynthaxError.INVALID_ARRAY_FIRST;

		while (!array_end) {
			char c = reader.nextNonWhitespace(err);
			if (c == ']') {
				if (!is_empty) {
					throw new JSONSyntaxException(err);
				}

				array_end = true;
				continue;
			}

			is_empty = false;
			err = JSONSynthaxError.INVALID_ARRAY_VALUE;

			array.add(parseNextValue(c));

			c = reader.nextNonWhitespace(JSONSynthaxError.INVALID_ARRAY_FOLLOW);
			if (c == ',') {
				continue;
			}

			if (c == ']') {
				array_end = true;
				continue;
			}

			throw new JSONSyntaxException(JSONSynthaxError.INVALID_ARRAY_FOLLOW);
		}

		return new JSONArray(array);
	}

	private JSONObject parseObject() throws JSONSyntaxException, IOException {
		JSONObject ob = new JSONObject();
		boolean object_end = false;
		boolean is_empty = true;

		JSONSynthaxError err = JSONSynthaxError.INVALID_OBJECT_FIRST;

		while (!object_end) {
			char c = reader.nextNonWhitespace(err);
			if (c == '}') {
				if (!is_empty) {
					throw new JSONSyntaxException(err);
				}

				object_end = true;
				continue;
			}

			if (c == '"') {
				is_empty = false;
				err = JSONSynthaxError.INVALID_OBJECT_NAME;
				String name = readNextString(JSONSynthaxError.INVALID_OBJECT_NAME);
				char c2 = reader.nextNonWhitespace(JSONSynthaxError.INVALID_OBJECT_SEPARATION);
				if (c2 != ':') {
					throw new JSONSyntaxException(JSONSynthaxError.INVALID_OBJECT_SEPARATION);
				}

				c2 = reader.nextNonWhitespace(JSONSynthaxError.INVALID_OBJECT_VALUE);

				if (!ob.addValue(name, parseNextValue(c2))) {
					throw new JSONSyntaxException(JSONSynthaxError.DUPLICATE_NAME);
				}

				c2 = reader.nextNonWhitespace(JSONSynthaxError.INVALID_OBJECT_FOLLOW);
				if (c2 == ',') {
					continue;
				}

				if (c2 == '}') {
					object_end = true;
					continue;
				}

				throw new JSONSyntaxException(JSONSynthaxError.INVALID_OBJECT_FOLLOW);
			}

			throw new JSONSyntaxException(err);
		}

		return ob;
	}

	private JSONString parseNextString() throws JSONSyntaxException, IOException {
		return new JSONString(readNextString(JSONSynthaxError.UNTERMINATED_STRING));
	}

	private String readNextString(JSONSynthaxError err) throws IOException, JSONSyntaxException {
		local_builder.setLength(0); // empty the builder
		while (reader.makeAvailable(1)) {
			int offset = 0;
			int max = reader.getAvailable();
			while (offset < max) {
				char nextChar = reader.peekCharacter(offset);
				if (nextChar == '"') {
					reader.appendNextString(local_builder, offset);
					reader.nextCharacter(); // advance onto '"'
					return local_builder.toString();
				}

				if (nextChar == '\\') {
					reader.appendNextString(local_builder, offset);
					reader.nextCharacter(); // advance onto '\\'
					local_builder.append(readEscaped());
					offset = -1;
					break;
				}

				if (Character.isISOControl(nextChar)) {
					throw new JSONSyntaxException(JSONSynthaxError.STRING_CONTAIN_CONTROL_CHAR);
				}

				++offset;
			}

			if (offset != -1) // we read all available characters
			{
				reader.appendNextString(local_builder, offset);
			}
		}

		throw new JSONSyntaxException(err);
	}

	private char readEscaped() throws IOException, JSONSyntaxException {
		if (!reader.makeAvailable(1)) {
			throw new JSONSyntaxException(JSONSynthaxError.UNFINISHED_ESCAPE_SEQUENCE);
		}

		switch (reader.nextCharacter()) {
			case '/':
				return '/';

			case '\\':
				return '\\';

			case '"':
				return '"';

			case 'b':
				return '\b';

			case 'f':
				return '\f';

			case 'n':
				return '\n';

			case 'r':
				return '\r';

			case 't':
				return '\t';

			case 'u':
				if (!reader.makeAvailable(4)) {
					throw new JSONSyntaxException(JSONSynthaxError.UNFINISHED_UNICODE_ESCAPE_SEQUENCE);
				}

				String hex = reader.nextString(4);
				try {
					return (char) Integer.parseInt(hex, 16);
				} catch (NumberFormatException e) {
					if (hex.contains("\"")) {
						throw new JSONSyntaxException(JSONSynthaxError.UNFINISHED_UNICODE_ESCAPE_SEQUENCE);
					}

					throw new JSONSyntaxException(JSONSynthaxError.INVALID_UNICODE_ESCAPE_SEQUENCE);
				}

			default:
				throw new JSONSyntaxException(JSONSynthaxError.INVALID_ESCAPE_SEQUENCE);
		}
	}

	private JSONValue parseNextLiteral(char c) throws IOException, JSONSyntaxException {
		local_builder.setLength(0);
		local_builder.append(c);
		while (reader.makeAvailable(1)) {
			char n = reader.peekCharacter(0);
			if (n == ']' || n == '}' || n == '\f' || n == ',' || n == '\b' || Character.isWhitespace(n)) {
				return decodeLiteral(local_builder.toString());
			}

			local_builder.append(reader.nextCharacter());
		}

		return decodeLiteral(local_builder.toString());
	}

	/**
	 * Decode the literal
	 *
	 * @param literal the literal to decode (sets the state)
	 * @throws JSONSyntaxException when the literal is not valid
	 */
	private JSONValue decodeLiteral(String literal) throws JSONSyntaxException {
		if (literal.equals("null")) {
			return JSONNull.Null;
		} else if (literal.equals("false")) {
			return JSONBoolean.False;
		} else if (literal.equals("true")) {
			return JSONBoolean.True;
		} else {
			try {
				return new JSONInteger(Long.parseLong(literal));
			} catch (NumberFormatException e) {
				try {
					return new JSONNumber(Double.parseDouble(literal));
				} catch (NumberFormatException e2) {
					throw new JSONSyntaxException(JSONSynthaxError.INVALID_LITERAL);
				}
			}
		}
	}

	@Override
	public void close() throws IOException {
		if (!closed) {
			closed = true;
			reader.close();
		}
	}

	/**
	 * Parse the JSON data
	 * <p>
	 * Subsequent calls will try to read the value following the previous one in the
	 * {@link JSONReader}
	 *
	 * @return The JSONValue parsed
	 * @throws JSONSyntaxException   if the JSON data is not valid
	 * @throws IOException           I/O error occurred (won't happen with
	 *                               {@link StringJSONReader})
	 * @throws IllegalStateException Thrown if the {@link JSONParser} is closed
	 * @see JSONParser#close() JSONParser::close()
	 */
	public JSONValue parse() throws JSONSyntaxException, IOException, IllegalStateException {
		if (closed) {
			throw new IllegalStateException("Cannot call member 'parse()' when the JSONParser is closed");
		}

		char c = reader.nextNonWhitespace(JSONSynthaxError.INVALID_DOCUMENT_START);
		return parseNextValue(c);
	}
}
