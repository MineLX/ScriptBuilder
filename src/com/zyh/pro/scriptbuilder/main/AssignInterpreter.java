package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

public class AssignInterpreter extends OperationInterpreter {

	public AssignInterpreter(ScriptContext context) {
		super(context);
	}

	@Override
	public IOperation interpret(String command) {
		IStringScanner scanner = new TrimmedStringScanner(new StringScanner(command));
		String left = scanner.nextPage();
		scanner.trim();
		scanner.nextChar();
		scanner.trim();
		String right = scanner.til(';');
		return new AssignOperation(context, left, new RightValue(context, right));
	}
}
