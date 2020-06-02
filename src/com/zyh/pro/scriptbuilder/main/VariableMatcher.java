package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.ToResult;
import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.value.VariableValue;

public class VariableMatcher implements ReturnMatcher<IValue, IStringScanner> {
	private final ScriptContext context;

	public VariableMatcher(ScriptContext context) {
		this.context = context;
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.existsIf(Character::isAlphabetic);
	}

	@Override
	public IValue onMatched(IStringScanner scanner) {
		return new VariableValue(context, scanner.nextPage());
	}
}
