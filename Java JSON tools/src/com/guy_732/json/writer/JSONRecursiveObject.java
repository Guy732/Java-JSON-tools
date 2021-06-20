package com.guy_732.json.writer;

import com.guy_732.json.exception.JSONException;

/**
 * Thrown when trying to write object containing itself
 * 
 * @author Guy_732
 */
public class JSONRecursiveObject extends JSONException
{

	private static final long serialVersionUID = 6513100252562741167L;

	public JSONRecursiveObject()
	{
		super();
	}

	public JSONRecursiveObject(String message)
	{
		super(message);
	}

	public JSONRecursiveObject(Throwable cause)
	{
		super(cause);
	}

	public JSONRecursiveObject(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JSONRecursiveObject(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
