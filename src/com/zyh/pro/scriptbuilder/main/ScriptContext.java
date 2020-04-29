package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.MatcherToResult;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scanner.main.TrimmedScanner;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class ScriptContext {

	private final List<Function> functions;

	private final PrintStream out;

	private final Map<String, String> variables;

	public ScriptContext(PrintStream out) {
		this.out = out;
		functions = new ArrayList<>();
		variables = new HashMap<>();
	}

	public Function getFunction(String name) {
		for (Function function : functions)
			if (function.getName().equals(name))
				return function;
		return null;
	}

	public void addFunction(Function function) {
		functions.add(function);
	}

	public PrintStream getOutputStream() {
		return out;
	}

	public String getVariable(String name) {
		return variables.get(name);
	}

	public void setVariable(String name, String value) {
		variables.put(name, value);
	}

	public String calculateRightValue(String rightValueExpr) {
		IScanner scanner = new TrimmedScanner(new Scanner(rightValueExpr));

//		ValueParser parser = new ValueParser();
//		IValue result = parser.parse(rightValueExpr);
//		return result.asString();

//		ReturnChain<ReturnValue, IScanner> build = new ReturnChain<ReturnValue, IScanner>()
//				.next(new FunctionInvocationChain(this));
		MatcherToResult<ReturnValue, IScanner> matcher =
				new MatcherToResult<>(new FunctionInvocationChain(this));

		IValue returnValue = matcher.get(scanner);
		if (returnValue != null)
			return returnValue.asString();

		if (scanner.existsIf(Character::isDigit)) {
			String value = scanner.nextFloat();
			if (!scanner.isEmpty()) {
				scanner.trim();
				scanner.nextChar();
				String secondValue = scanner.nextFloat();
				value = valueOf(parseInt(secondValue) + parseInt(value));
			}
			return value;
		}
		return "#######";
	}
}
