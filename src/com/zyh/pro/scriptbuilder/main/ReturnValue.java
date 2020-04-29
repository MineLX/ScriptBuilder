package com.zyh.pro.scriptbuilder.main;

public class ReturnValue implements IValue {
	private String value;

	public ReturnValue(String value) {
		this.value = value;
	}

	@Override
	public String asString() {
		return value;
	}
}
