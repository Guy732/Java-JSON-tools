package com.guy_732.json.reader;

import com.guy_732.json.JSONArray;
import com.guy_732.json.JSONBoolean;
import com.guy_732.json.JSONNull;
import com.guy_732.json.JSONObject;
import com.guy_732.json.JSONValue;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class parse JSON data with the method {@link JSONParser#parse()
 * JSONParser::parse()} and returns a {@link JSONValue}
 *
 * @author Guy_732
 */
public class JSONParser implements Closeable {
	private static final String expectedTokens = "expected tokens: `null', `false', `true', `\"', `[', `{' or number";

	private final StreamTokenizer tokenizer;
	private final Reader underlyingReader;

	private boolean closed = false;

	/**
	 * Construct a new {@link JSONParser}
	 *
	 * @param reader The {@link Reader} to read from.
	 * @throws NullPointerException If the given {@link Reader} is null
	 */
	public JSONParser(Reader reader) throws NullPointerException {
		this.underlyingReader = Objects.requireNonNull(reader);
		this.tokenizer = new StreamTokenizer(reader);
		initializeTokenizer();
	}

	private void initializeTokenizer() {
		final char[] allWhitespace = new char[] {' ', '\t', '\n', '\r'};
		final char[] extraWordChars = new char[] {'+', '-', '.', 'E'};
		StreamTokenizer t = tokenizer;
		t.resetSyntax();
		t.eolIsSignificant(false);

		for (char c : allWhitespace)
			t.whitespaceChars(c, c);

		t.wordChars('a', 'z');
		t.wordChars('0', '9');
		for (char c : extraWordChars)
			t.wordChars(c, c);

		t.quoteChar('"');

		supportCStyleComment(false);
	}

	/**
	 * Construct a new {@link JSONParser}
	 *
	 * @param stream The stream to read from
	 * @throws NullPointerException if stream is null
	 */
	public JSONParser(InputStream stream) throws NullPointerException {
		this(new InputStreamReader(stream));
	}

	/**
	 * Creates a new {@link JSONParser}
	 *
	 * @param s The {@link String} storing the JSON data.
	 * @throws NullPointerException If 's' is null
	 */
	public JSONParser(String s) throws NullPointerException {
		this(new StringReader(s));
	}

	/**
	 * Creates a new {@link JSONParser}
	 *
	 * @param value The char[] storing the JSON data
	 * @throws NullPointerException If 'value' is null
	 */
	public JSONParser(char[] value) throws NullPointerException {
		this(String.copyValueOf(value));
	}

	@Override
	public void close() throws IOException {
		if (closed)
			return;

		closed = true;
		underlyingReader.close();
	}

	private void ensureOpen() throws IOException {
		if (closed)
			throw new IOException("The parser is closed");
	}

	/**
	 * Enable/Disable support for C/C++ style comments (// until end of line and /* until * /)
	 * This is NOT in the json standard, hence, it is disabled by default
	 *
	 * @param flag whether C-Style comments are enabled or not
	 */
	public void supportCStyleComment(boolean flag) {
		tokenizer.slashStarComments(flag);
		tokenizer.slashSlashComments(flag);
	}

	public JSONValue parse() throws IOException, JSONSyntaxException {
		ensureOpen();
		if (tokenizer.nextToken() == StreamTokenizer.TT_EOF)
			throw new EOFException("The parser is at End Of File, nothing to be parsed");
		tokenizer.pushBack();
		return parseNextValue();
	}

	private JSONValue parseNextValue() throws IOException, JSONSyntaxException {
		switch (tokenizer.nextToken()) {
			case StreamTokenizer.TT_EOF:
				throw new EOFException("The parser reached End Of File while parsing values");
			case StreamTokenizer.TT_WORD:
				return parseWord();
			case '[':
				return parseArray();
			case '{':
				return parseObject();
			case '"':
				return JSONValue.valueOf(tokenizer.sval);
			default:
				break;
		}

		String err = String.format("Unknown token `%c'", tokenizer.ttype);
		throw new JSONSyntaxException(err,
				expectedTokens,
				tokenizer
		);
	}

	private JSONObject parseObject() throws IOException, JSONSyntaxException {
		throw new RuntimeException("Method `parseObject()' not yet implemented");
	}

	private JSONArray parseArray() throws IOException, JSONSyntaxException {
		throw new RuntimeException("Method `parseArray()' not yet implemented");
	}

	private JSONValue parseWord() throws JSONSyntaxException {
		switch (tokenizer.sval) {
			case "null":
				return JSONNull.Null;
			case "true":
				return JSONBoolean.True;
			case "false":
				return JSONBoolean.False;
			default:
				break;
		}

		JSONValue val = null;
		Scanner scan = new Scanner(tokenizer.sval);
		if (scan.hasNextLong())
			val = JSONValue.valueOf(scan.nextLong());
		else if (scan.hasNextDouble())
			val = JSONValue.valueOf(scan.nextDouble());

		if (val != null && !scan.hasNext())
			return val;
		String err = String.format("Unknown token `%s'", tokenizer.sval);
		throw new JSONSyntaxException(err,
				expectedTokens,
				tokenizer);
	}
}
