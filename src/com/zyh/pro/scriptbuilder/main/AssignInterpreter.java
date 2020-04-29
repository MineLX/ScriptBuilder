package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scanner.main.TrimmedScanner;

public class AssignInterpreter extends OperationInterpreter {

	public AssignInterpreter(ScriptContext context) {
		super(context);
	}

	@Override
	public IOperation interpret(String command) {
		IScanner scanner = new TrimmedScanner(new Scanner(command));
		String left = scanner.nextPage();
		scanner.trim();
		scanner.nextChar();
		scanner.trim();
		String right = scanner.til(';');
		return new AssignOperation(context, left, new RightValue(context, right));
	}
}
