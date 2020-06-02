package com.zyh.pro.scriptbuilder.main.value;

import com.zyh.pro.scriptbuilder.main.Params;
import com.zyh.pro.scriptbuilder.main.ScriptContext;

public class FunctionValue implements IValue {

	private final ScriptContext context;
	private final String name;
	private final Params params;

	public FunctionValue(ScriptContext context, String name, Params params) {
		this.context = context;
		this.name = name;
		this.params = params;
	}

	@Override
	public String asString() {
		return context.getFunction(name).execute(params).asString();
	}
}
