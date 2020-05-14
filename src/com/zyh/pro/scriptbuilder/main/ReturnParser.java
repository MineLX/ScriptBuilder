package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ToResult;

public class ReturnParser implements ToResult<IOperation, IStringScanner> {

	private final ScriptContext context;

	private final ValuesParser parser;

	public ReturnParser(ScriptContext context) {
		this.context = context;
		parser = new ValuesParser(context);
	}

	@Override
	public IOperation get(IStringScanner scanner) {
		scanner.pass("return");
		scanner.trim();
		IValue returnValue = parser.parse(scanner);
		scanner.nextChar();
		return new ReturnOperation(context, returnValue);
	}
}
