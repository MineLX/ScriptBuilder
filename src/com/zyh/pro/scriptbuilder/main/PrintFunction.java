package com.zyh.pro.scriptbuilder.main;

public class PrintFunction extends Function {

	public PrintFunction(ScriptContext context) {
		super(context, "print");
	}

	@Override
	public Value execute(Params params) {
		IValue value = params.get(0);
		context.getOutputStream().print(value.asString());
		return null;
	}
}
