package com.zyh.pro.scriptbuilder.main;

public class AssignOperation implements IOperation {
	private final ScriptContext context;
	private final String name;
	private final IValue value;

	public AssignOperation(ScriptContext context, String name, IValue value) {
		this.context = context;
		this.name = name;
		this.value = value;
	}

	@Override
	public void execute() {
//		context.setVariable(name, value.asString());
		context.setVariable(name, value);
	}
}
