package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnChain;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class FunctionInvocationChain implements ReturnMatcher<ReturnValue, IScanner> {

	private final ScriptContext context;

	public FunctionInvocationChain(ScriptContext context) {
		this.context = context;
	}

	@Override
	public ReturnValue onMatched(IScanner scanner) {
		Function function = context.getFunction(scanner.nextPage());
		Params params = new ParamsParser(context)
				.parse(scanner.between('(', ')'));
		return function.execute(params);
	}

	@Override
	public boolean isMatch(IScanner scanner) {
		return scanner.existsIf(Character::isAlphabetic);
	}
}
