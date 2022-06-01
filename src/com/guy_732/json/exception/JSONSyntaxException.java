package com.guy_732.json.exception;

import com.guy_732.json.reader.JSONSynthaxError;

/**
 * Class thrown when a Parser cannot parse due to unknown Syntax
 *
 * @author Guy_732
 */
public class JSONSyntaxException extends JSONException {
	private static final long serialVersionUID = -5679459782582941908L;

	private final JSONSynthaxError error;

	/**
	 * Creates a new {@link JSONSynthaxError} for the given components.
	 *
	 * @param error The syntax error.
	 */
	public JSONSyntaxException(JSONSynthaxError error) {
		super(error.getDescription());
		this.error = error;
	}

	@Override
	public String getMessage() {
		return error.getDescription() + " (" +
				error.getExpectation() +
				')';
	}
}
