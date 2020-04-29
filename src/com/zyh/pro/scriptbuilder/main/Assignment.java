package com.zyh.pro.scriptbuilder.main;

public class Assignment {
	private final String variableName;
	private final String variableValue;

	public Assignment(String variableName, String variableValue) {
		this.variableName = variableName;
		this.variableValue = variableValue;
	}

	public String getLeft() {
		return variableName;
	}

	public String getRight() {
		return variableValue;
	}
}
