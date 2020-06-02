package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.value.Value;

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
