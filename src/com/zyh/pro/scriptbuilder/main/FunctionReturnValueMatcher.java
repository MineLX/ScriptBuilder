package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;
import com.zyh.pro.scriptbuilder.main.parser.ParamsParser;
import com.zyh.pro.scriptbuilder.main.value.FunctionValue;
import com.zyh.pro.scriptbuilder.main.value.IValue;

public class FunctionReturnValueMatcher implements ReturnMatcher<IValue, IStringScanner> {

	private final ScriptContext context;

	public FunctionReturnValueMatcher(ScriptContext context) {
		this.context = context;
	}

	@Override
	public boolean isMatch(IStringScanner s) {
		if (!s.existsIf(Character::isAlphabetic))
			return false;
		IStringScanner copy = s.copy();
		copy.nextPage();
		return copy.exists("(");
	}

	@Override
	public IValue onMatched(IStringScanner scanner) {
		String functionName = scanner.nextPage();
		scanner.nextChar(); // '('
		Params params = new ParamsParser(context).parse(scanner);
		scanner.nextChar(); // ')'
		return new FunctionValue(context, functionName, params);
	}
}
