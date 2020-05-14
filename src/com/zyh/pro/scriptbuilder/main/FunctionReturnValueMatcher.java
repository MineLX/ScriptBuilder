package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

public class FunctionReturnValueMatcher implements ReturnMatcher<IValue, String> {

	private final ScriptContext context;

	public FunctionReturnValueMatcher(ScriptContext context) {
		this.context = context;
	}

	@Override
	public boolean isMatch(String s) {
		return s.contains("(");
	}

	@Override
	public IValue onMatched(String s) {
		IStringScanner scanner = new TrimmedStringScanner(new StringScanner(s));
		String functionName = scanner.nextPage();
		scanner.nextChar(); // '('
		Params params = new ParamsParser(context).parse(scanner);
		scanner.nextChar(); // ')'
		return new FunctionValue(context, functionName, params);
	}
}
