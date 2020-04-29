package com.zyh.pro.scriptbuilder.main;

public class InvokeFunctionOperation implements IOperation {

	private final Function function;
	private final Params params;

	public InvokeFunctionOperation(Function function, Params params) {
		this.params = params;
		this.function = function;
	}

	@Override
	public void execute() {
		function.execute(params);
	}
}
