package com.guy_732.json.writer;

import com.guy_732.json.JSONType;
import com.guy_732.json.exception.JSONException;

public class JSONUnknownType extends JSONException
{
	private static final long serialVersionUID = 7270320068228436883L;
	
	private final JSONType type;

	public JSONUnknownType()
	{
		type = null;
	}

	public JSONUnknownType(String message)
	{
		super(message);
		type = null;
	}

	public JSONUnknownType(Throwable cause)
	{
		super(cause);
		type = null;
	}

	public JSONUnknownType(String message, Throwable cause)
	{
		super(message, cause);
		type = null;
	}

	public JSONUnknownType(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
		type = null;
	}
	
	public JSONUnknownType(JSONType type)
	{
		this.type = type;
	}
	
	public String getMessage()
	{
		if (type == null)
		{
			return super.getMessage();
		}
		
		StringBuilder msg = new StringBuilder();
		msg.append("Unknown JSONType '");
		msg.append(type.name());
		msg.append("'");
		return msg.toString();
	}
	
}
