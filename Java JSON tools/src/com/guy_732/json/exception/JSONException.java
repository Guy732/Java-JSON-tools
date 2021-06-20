package com.guy_732.json.exception;

/**
 * Base class for all errors involving JSON
 * 
 * @author Guy_732
 */
public class JSONException extends RuntimeException
{
	private static final long serialVersionUID = -2802262093645047750L;

	public JSONException()
	{
		super();
	}

	public JSONException(String message)
	{
		super(message);
	}

	public JSONException(Throwable cause)
	{
		super(cause);
	}

	public JSONException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JSONException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
