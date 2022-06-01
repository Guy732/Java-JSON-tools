package com.guy_732.json.reader;

/**
 * A {@link JSONSynthaxError} describes the kinds of syntax error a JSON parser
 * may encounter.
 */
public enum JSONSynthaxError {
	/**
	 * Describes an invalid document, where the initial character does not exist
	 */
	INVALID_DOCUMENT_START("Invalid document start",
			"Expected '[', '{', '\"', 'null', 'false', 'true' or number for a value"),

	/**
	 * Describes an invalid JSON array, where the initial <tt>[</tt> isn't followed
	 * by a JSON value or a <tt>]</tt>.
	 */
	INVALID_ARRAY_FIRST("Invalid array", "Expected value or ']' after '['"),

	/**
	 * Describes an invalid JSON array, where a value isn't followed by a JSON
	 * <tt>,</tt> or a <tt>]</tt>.
	 */
	INVALID_ARRAY_FOLLOW("Invalid array", "Expected ',' or ']' after value"),

	/**
	 * Describes an invalid JSON array, where a <tt>,</tt> isn't followed by a JSON
	 * value.
	 */
	INVALID_ARRAY_VALUE("Invalid array", "Expected value after ','"),

	/**
	 * Describes an invalid JSON object, where the initial <tt>{</tt> isn't followed
	 * by a JSON name or a <tt>}</tt>.
	 */
	INVALID_OBJECT_FIRST("Invalid object", "Expected name or '}' after '{'"),

	/**
	 * Describes an invalid JSON object, where a value isn't followed by a
	 * <tt>.</tt> or a <tt>}</tt>.
	 */
	INVALID_OBJECT_FOLLOW("Invalid object", "Expected ',' or '}' after value"),

	/**
	 * Describes an invalid JSON object, where a <tt>,</tt> isn't followed by a JSON
	 * name.
	 */
	INVALID_OBJECT_NAME("Invalid object", "Expected name after ','"),

	/**
	 * Describes an invalid JSON object, where a JSON name isn't followed by a
	 * <tt>:</tt>.
	 */
	INVALID_OBJECT_SEPARATION("Invalid object", "Expected ':' after name"),

	/**
	 * Describes an invalid JSON object, where a <tt>:</tt> isn't followed by a JSON
	 * value.
	 */
	INVALID_OBJECT_VALUE("Invalid object", "Expected value after ':'"),

	/**
	 * Describes an invalid JSON string, that is not ended by a <tt>"</tt>.
	 */
	UNTERMINATED_STRING("Unterminated string", "Expected '\"'"),

	/**
	 * Describes an invalid JSON string, where a <tt>\</tt> isn't followed by at
	 * least one character.
	 */
	UNFINISHED_ESCAPE_SEQUENCE("Unfinished escapse sequence", "Expected at least one character after '\\'"),

	/**
	 * Describes an invalid JSON string, where a <tt>\\</tt> isn't followed by a
	 * valid escape sequence.
	 */
	INVALID_ESCAPE_SEQUENCE("Invalid escape sequence",
			"Expected '\"', '\\', 'b', 'f', 'r', 'n', 't' or 'uXXXX' after '\\'"),

	/**
	 * Describes an invalid JSON string, where a <tt>\\u</tt> isn't followed by four
	 * character.
	 */
	UNFINISHED_UNICODE_ESCAPE_SEQUENCE("Unfinished unicode escapse sequence", "Expected four characters after '\\u'"),

	/**
	 * Describes an invalid JSON string, where a <tt>\\u</tt> isn't followed by four
	 * hex characters.
	 */
	INVALID_UNICODE_ESCAPE_SEQUENCE("Invalid unicode escapse sequence",
			"Expected four hexadecimal characters after '\\u'"),

	/**
	 * Describes an invalid JSON value, that does't begin with <tt>[</tt>,
	 * <tt>{</tt> or <tt>"</tt> and isn't a valid JSON literal.
	 */
	INVALID_LITERAL("Invalid literal",
			"Expected 'null', 'false', 'true' or a number for a value that doesn't start with '[', '{' or '\"'"),

	/**
	 * Describe an Object having duplicate names.
	 */
	DUPLICATE_NAME("Duplicate name", "An object have duplicate name."),

	/**
	 * Describe a String having control characters within.
	 */
	STRING_CONTAIN_CONTROL_CHAR("Invalid string, has control character",
			"A String cannot contain control characters, use escape sequence instead.");

	private final String description;

	private final String expectation;

	JSONSynthaxError(String description, String expectation) {
		this.description = description;
		this.expectation = expectation;
	}

	/**
	 * Returns a description of this {@link JSONSynthaxError}.
	 *
	 * @return A description of this {@link JSONSynthaxError}.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns a description of an unfulfilled expectation, that caused this
	 * {@link JSONSynthaxError}.
	 *
	 * @return a description of an unfulfilled expectation, that caused this
	 * {@link JSONSynthaxError}.
	 */
	public String getExpectation() {
		return expectation;
	}
}
