package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.value.Value;

import static java.lang.String.valueOf;

public class SumFunction extends Function {
	public SumFunction(ScriptContext context) {
		super(context, "sum");
	}

	@Override
	public Value execute(Params params) {
		return new Value(valueOf(
				params.getParams().stream()
						.map(IValue::asString).map(Integer::parseInt)
						.reduce(0, Integer::sum)));
	}
}
