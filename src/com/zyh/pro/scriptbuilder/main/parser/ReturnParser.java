package com.zyh.pro.scriptbuilder.main.parser;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scriptbuilder.main.operation.IOperation;
import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.operation.ReturnOperation;
import com.zyh.pro.scriptbuilder.main.ScriptContext;

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
