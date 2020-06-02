package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class ReturnParser implements ReturnMatcher<IOperation, IStringScanner> {

	private final ScriptContext context;

	private final ValuesParser parser;

	public ReturnParser(ScriptContext context) {
		this.context = context;
		parser = new ValuesParser(context);
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.exists("return");
	}

	@Override
	public IOperation onMatched(IStringScanner scanner) {
		scanner.pass("return");
		scanner.trim();
		IValue returnValue = parser.parse(scanner);
		scanner.nextChar();
		return new ReturnOperation(context, returnValue);
	}
}
