package com.guy_732.json.reader;

import com.guy_732.json.exception.JSONException;

import java.io.StreamTokenizer;

/**
 * Class thrown when a Parser cannot parse due to unknown Syntax
 *
 * @author Guy_732
 */
public class JSONSyntaxException extends JSONException {
	private static final long serialVersionUID = -5679459782582941908L;
	private final String expected;
	private final int line;

	/**
	 * Creates a new Syntax Exception
	 *
	 * @param error Description of the error
	 * @param expected Describe what was expected
	 *                 May be null
	 * @param tokenizer The StreamTokenizer generating the error, calling {@link StreamTokenizer#lineno()}
	 *                  to get the location
	 */
	public JSONSyntaxException(String error, String expected, StreamTokenizer tokenizer) {
		this(error, expected, tokenizer.lineno());
	}

	/**
	 * Creates a new Syntax Exception
	 *
	 * @param error Description of the error
	 * @param expected Describe what was expected
	 *                 May be null
	 * @param errorLine The line the error occurred on (in the input)
	 */
	public JSONSyntaxException(String error, String expected, int errorLine) {
		super(error);
		this.expected = expected;
		this.line = errorLine;
	}

	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder(super.getMessage());
		if (expected != null) {
			builder.append(" (");
			builder.append(expected);
			builder.append(")");
		}

		builder.append(" at line ");
		builder.append(line);
		return builder.toString();
	}
}
