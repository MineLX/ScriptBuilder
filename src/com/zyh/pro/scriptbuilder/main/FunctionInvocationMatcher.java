package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.ReturnMatcher;

public class FunctionInvocationMatcher implements ReturnMatcher<IValue, String> {
	private final ScriptContext context;

	FunctionInvocationMatcher(ScriptContext context) {
		this.context = context;
	}

	@Override
	public boolean isMatch(String s) {
		return s.contains("(");
	}

	@Override
	public IValue onMatched(String s) {
		return toFunctionValue(s);
	}

	private IValue toFunctionValue(String leafValueAsText) {
		FunctionInvocation parse = new FunctionInvocationParser(context).parse(leafValueAsText);
		return new FunctionValue(context, parse.getFunctionName(), parse.getParams());
	}
}
