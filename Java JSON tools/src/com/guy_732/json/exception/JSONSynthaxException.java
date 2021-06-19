package com.guy_732.json.exception;

import com.guy_732.json.reader.JSONSynthaxError;

public class JSONSynthaxException extends JSONException
{
	private static final long serialVersionUID = -5679459782582941908L;
	
	private final JSONSynthaxError error;
	
	/**
	 * Creates a new {@link JSONSynthaxError} for the given components.
	 * 
	 * @param error
	 *            The syntax error.
	 */
	public JSONSynthaxException(JSONSynthaxError error)
	{
		super(error.getDescription());
		this.error = error;
	}
	
	@Override
	public String getMessage()
	{
		StringBuilder s = new StringBuilder(error.getDescription());
		s.append(" (");
		s.append(error.getExpectation());
		s.append(')');
		
		return s.toString();
	}
}
