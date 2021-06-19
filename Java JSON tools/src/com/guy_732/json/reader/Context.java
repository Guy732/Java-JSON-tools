package com.guy_732.json.reader;

public enum Context
{
	/**
	 * Original state, before doing anything
	 */
	BEGIN,
	
	/**
	 * When the last value has been parsed.
	 */
	END,
	
	/**
	 * The state will also be CLOSED in this case.
	 */
	CLOSED
}
