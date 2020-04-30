package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.StringScanner;

public class VariableMatcher implements ReturnMatcher<IValue, String> {
	private final ScriptContext context;

	VariableMatcher(ScriptContext context) {
		this.context = context;
	}

	@Override
	public boolean isMatch(String variableValueAsText) {
		return new StringScanner(variableValueAsText).existsIf(Character::isAlphabetic);
	}

	@Override
	public IValue onMatched(String variableValueAsText) {
		return new VariableValue(context, variableValueAsText);
	}
}
