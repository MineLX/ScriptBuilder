package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class FunctionInvocationChain implements ReturnMatcher<ReturnValue, IStringScanner> {

	private final ScriptContext context;

	public FunctionInvocationChain(ScriptContext context) {
		this.context = context;
	}

	@Override
	public ReturnValue onMatched(IStringScanner scanner) {
		Function function = context.getFunction(scanner.nextPage());
		Params params = new ParamsParser(context)
				.parse(scanner.between('(', ')'));
		System.out.println("params = " + params);
		return function.execute(params);
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.existsIf(Character::isAlphabetic);
	}
}
