package com.zyh.pro.scriptbuilder.main.operation;

import com.zyh.pro.scriptbuilder.main.Params;
import com.zyh.pro.scriptbuilder.main.ScriptContext;

public class InvokeFunctionOperation implements IOperation {

	private final ScriptContext context;

	private final Params params;

	private final String functionName;

	public InvokeFunctionOperation(ScriptContext context, String functionName, Params params) {
		this.context = context;
		this.functionName = functionName;
		this.params = params;
	}

	@Override
	public void execute() {
		context.getFunction(functionName).execute(params);
	}
}
