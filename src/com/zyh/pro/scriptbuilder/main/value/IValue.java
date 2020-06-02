package com.zyh.pro.scriptbuilder.main.value;

public interface IValue {
	String asString();

	static IValue empty() {
		return () -> "EMPTY";
	}
}
