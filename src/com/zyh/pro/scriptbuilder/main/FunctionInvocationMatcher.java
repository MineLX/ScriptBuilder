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
		FunctionInvocation invocation = new FunctionInvocationParser(context).parse(s);
		System.out.println("invocation = " + invocation);
		return new FunctionValue(context, invocation.getFunctionName(), invocation.getParams());
	}
}
