package com.zyh.pro.scriptbuilder.main;

public class Function {
	protected final ScriptContext context;
	private final String name;

	public Function(ScriptContext context, String name) {
		this.context = context;
		this.name = name;
	}

	public ReturnValue execute(Params params) {
		return null;
	}

	public String getName() {
		return name;
	}
}
