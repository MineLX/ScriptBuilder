package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

public class AssignParser {

	private final ScriptContext context;

	private final ValuesParser valuesParser;

	public AssignParser(ScriptContext context) {
		this.context = context;
		valuesParser = new ValuesParser(context);
	}

	public IOperation onMatched(IStringScanner scanner) {
		String variableName = scanner.nextPage();
		scanner.nextChar(); // '='
		IValue variableValue = valuesParser.parse(scanner);
		scanner.nextChar(); // ';'
		return new AssignOperation(context, variableName, variableValue);
	}
}
