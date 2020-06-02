package com.zyh.pro.scriptbuilder.main.parser;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scriptbuilder.main.operation.IOperation;
import com.zyh.pro.scriptbuilder.main.operation.InvokeFunctionOperation;
import com.zyh.pro.scriptbuilder.main.Params;
import com.zyh.pro.scriptbuilder.main.ScriptContext;

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
