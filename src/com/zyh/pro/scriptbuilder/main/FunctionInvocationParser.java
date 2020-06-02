package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

public class FunctionInvocationParser implements ReturnMatcher<IOperation, IStringScanner> {

	private final ScriptContext context;

	public FunctionInvocationParser(ScriptContext context) {
		this.context = context;
	}

	@Override
	public IOperation onMatched(IStringScanner scanner) {
		String functionName = scanner.nextPage();
		scanner.nextChar(); // '('
		Params params = new ParamsParser(context).parse(scanner);
		scanner.nextChar(); // ')'
		scanner.nextChar(); // ';'
		return new InvokeFunctionOperation(
				context,
				functionName,
				params);
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		IStringScanner copy = scanner.copy();
		if (!copy.existsIf(Character::isAlphabetic))
			return false;
		copy.nextPage();
		return copy.exists("(");
	}
}
