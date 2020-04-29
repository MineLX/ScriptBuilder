package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.Scanner;

public class InvokeFunctionInterpreter extends OperationInterpreter {

	public InvokeFunctionInterpreter(ScriptContext context) {
		super(context);
	}

	@Override
	public IOperation interpret(String command) {
		Scanner scanner = new Scanner(command);
		String functionName = scanner.nextPage();
		String paramsAsText = scanner.between('(', ')');

		return new InvokeFunctionOperation(
				context.getFunction(functionName),
				new ParamsParser(context).parse(paramsAsText));
	}
}
