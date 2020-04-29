package com.zyh.pro.scriptbuilder.main;

public abstract class OperationInterpreter {

	protected final ScriptContext context;

	public OperationInterpreter(ScriptContext context) {
		this.context = context;
	}

	public abstract IOperation interpret(String command);

	public static OperationInterpreter empty() {
		return new OperationInterpreter(null) {
			@Override
			public IOperation interpret(String command) {
				return IOperation.empty();
			}
		};
	}
}
