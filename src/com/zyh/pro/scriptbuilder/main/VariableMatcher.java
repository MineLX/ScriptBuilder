package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.Scanner;

public class VariableMatcher implements ReturnMatcher<IValue, String> {
	private final ScriptContext context;

	VariableMatcher(ScriptContext context) {
		this.context = context;
	}

	private IValue toVariableValue(String leafValueAsText) {
		return new VariableValue(context, leafValueAsText);
	}

	@Override
	public boolean isMatch(String s) {
		return new Scanner(s).existsIf(Character::isAlphabetic);
	}

	@Override
	public IValue onMatched(String s) {
		return toVariableValue(s);
	}
}
