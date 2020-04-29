package com.zyh.pro.scriptbuilder.main;

public class RightValue implements IValue {
	private final ScriptContext context;
	private final String command;

	public RightValue(ScriptContext context, String command) {
		this.context = context;
		this.command = command;
	}

	@Override
	public String asString() {
		return context.calculateRightValue(command);
	}
}
