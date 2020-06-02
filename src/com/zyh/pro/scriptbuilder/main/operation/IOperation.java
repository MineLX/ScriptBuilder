package com.zyh.pro.scriptbuilder.main.operation;

public interface IOperation {
	void execute();

	static IOperation doNothing() {
		return () -> {
		};
	}
}
