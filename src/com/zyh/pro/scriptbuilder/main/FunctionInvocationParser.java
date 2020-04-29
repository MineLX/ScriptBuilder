package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.Scanner;

public class FunctionInvocationParser {

	private final ScriptContext context;

	public FunctionInvocationParser(ScriptContext context) {
		this.context = context;
	}

	public FunctionInvocation parse(String functionInvocationAsText) {
		Scanner scanner = new Scanner(functionInvocationAsText);
		String functionName = scanner.nextPage();
		String paramsAsText = scanner.between('(', ')');
		Params params = new ParamsParser(context).parse(paramsAsText);

		FunctionInvocation result = new FunctionInvocation(functionName);
		result.addParam(params);
		return result;
	}
}
