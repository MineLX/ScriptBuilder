package com.zyh.pro.scriptbuilder.main;

public interface IValue {
	String asString();

	static IValue empty() {
		return () -> "EMPTY";
	}
}
