package com.zyh.pro.scriptbuilder.main;

import java.util.List;

public class OperationsFunction extends Function {

	private final IOperation operation;

	private final List<String> modelParams;

	public OperationsFunction(ScriptContext context, String functionName, IOperation operation, List<String> modelParams) {
		super(context, functionName);
		this.operation = operation;
		this.modelParams = modelParams;
	}

	@Override
	public IValue execute(Params params) {
		context.pushFunctionFrame(modelParams, params);
		operation.execute();
		IValue result = context.getFunctionFrame().getReturnValue();
		context.popFunctionFrame();
		return result;
	}
}
