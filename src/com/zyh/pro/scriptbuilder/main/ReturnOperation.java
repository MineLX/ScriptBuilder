package com.zyh.pro.scriptbuilder.main;

public class ReturnOperation implements IOperation {

	private final ScriptContext context;

	private final IValue returnValue;

	public ReturnOperation(ScriptContext context, IValue returnValue) {
		this.context = context;
		this.returnValue = returnValue;
	}

	@Override
	public void execute() {
		context.getFunctionFrame().setReturnValue(returnValue);
	}
}
