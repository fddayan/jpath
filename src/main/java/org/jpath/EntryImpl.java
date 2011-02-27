package org.jpath;
/**
 * 
 */


import java.util.Map;

public class EntryImpl implements Map.Entry<String,String> {

	String key;

	String value;


	public EntryImpl(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String setValue(String value) {
		this.value = value;

		return this.value;
	}

}