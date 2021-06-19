package com.guy_732.json.exception;

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
