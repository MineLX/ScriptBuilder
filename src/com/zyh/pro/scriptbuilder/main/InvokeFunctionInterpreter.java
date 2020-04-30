package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.StringScanner;

public class InvokeFunctionInterpreter extends OperationInterpreter {

	public InvokeFunctionInterpreter(ScriptContext context) {
		super(context);
	}

	@Override
	public IOperation interpret(String command) {
		StringScanner scanner = new StringScanner(command);
		String functionName = scanner.nextPage();
		String paramsAsText = scanner.between('(', ')');

		return new InvokeFunctionOperation(
				context.getFunction(functionName),
				new ParamsParser(context).parse(paramsAsText));
	}
}
