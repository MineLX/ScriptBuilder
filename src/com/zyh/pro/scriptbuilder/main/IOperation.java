package com.zyh.pro.scriptbuilder.main;

public interface IOperation {
	void execute();

	static IOperation empty() {
		return () -> {};
	}
}
