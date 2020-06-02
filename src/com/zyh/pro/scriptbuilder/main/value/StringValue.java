package com.zyh.pro.scriptbuilder.main.value;

public class StringValue implements IValue {
	private final String value;

	public StringValue(String value) {
		this.value = value;
	}

	@Override
	public String asString() {
		return value.substring(1, value.length() - 1);
	}
}
