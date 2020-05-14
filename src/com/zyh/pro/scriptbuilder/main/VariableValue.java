package com.zyh.pro.scriptbuilder.main;

public class VariableValue implements IValue {
	private final ScriptContext context;
	private final String variableName;

	public VariableValue(ScriptContext context, String variableName) {
		this.context = context;
		this.variableName = variableName;
	}

	@Override
	public String asString() {
		return context.getVariable(variableName).asString();
	}
}
