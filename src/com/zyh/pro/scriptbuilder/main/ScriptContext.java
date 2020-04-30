package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.MatcherToResult;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

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
		IStringScanner scanner = new TrimmedStringScanner(new StringScanner(rightValueExpr));

//		ValueParser parser = new ValueParser();
//		IValue result = parser.parse(rightValueExpr);
//		return result.asString();

//		ReturnChain<ReturnValue, IStringScanner> build = new ReturnChain<ReturnValue, IStringScanner>()
//				.next(new FunctionInvocationChain(this));
		MatcherToResult<ReturnValue, IStringScanner> matcher =
				new MatcherToResult<>(new FunctionInvocationChain(this));
//
//		System.out.println("rightValueExpr = " + rightValueExpr);
//		IValue returnValue = matcher.get(scanner);


		ValueParser parser = new ValueParser(this);
		System.out.println("scanner = " + scanner);
		IValue parse = parser.parse(scanner);
		System.out.println("scanner = " + scanner);
		return parse.asString();

//		System.out.println("returnValue = " + returnValue.asString());

//		if (returnValue != null)
//			return returnValue.asString();
//
//		if (scanner.existsIf(Character::isDigit)) {
//			String value = scanner.nextFloat();
//			if (!scanner.isEmpty()) {
//				scanner.trim();
//				scanner.nextChar();
//				String secondValue = scanner.nextFloat();
//				value = valueOf(parseInt(secondValue) + parseInt(value));
//			}
//			return value;
//		}
//		return "#######";
	}
}
