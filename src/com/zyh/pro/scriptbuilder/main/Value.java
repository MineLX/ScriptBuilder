package com.zyh.pro.scriptbuilder.main;

public class Value implements IValue {
	private final String valueAsText;

	public Value(String valueAsText) {
		this.valueAsText = valueAsText;
	}

	@Override
	public String asString() {
		return valueAsText;
	}
}
