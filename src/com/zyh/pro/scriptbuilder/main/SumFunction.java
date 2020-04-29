package com.zyh.pro.scriptbuilder.main;

import static java.lang.String.valueOf;

public class SumFunction extends Function {
	public SumFunction(ScriptContext context) {
		super(context, "sum");
	}

	@Override
	public ReturnValue execute(Params params) {
		return new ReturnValue(valueOf(
				params.getParams().stream()
						.map(IValue::asString).map(Integer::parseInt)
						.reduce(0, Integer::sum)));
	}
}
