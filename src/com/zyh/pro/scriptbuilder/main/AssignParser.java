package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

public class AssignParser implements ReturnMatcher<IOperation, IStringScanner> {

	private final ScriptContext context;

	private final ValuesParser valuesParser;

	public AssignParser(ScriptContext context) {
		this.context = context;
		valuesParser = new ValuesParser(context);
	}

	@Override
	public IOperation onMatched(IStringScanner scanner) {
		String variableName = scanner.nextPage();
		scanner.nextChar(); // '='
		IValue variableValue = valuesParser.parse(scanner);
		scanner.nextChar(); // ';'
		return new AssignOperation(context, variableName, variableValue);
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		IStringScanner copy = scanner.copy();

		if (copy.existsIf(Character::isAlphabetic)) {
			copy.nextPage();
			return copy.exists("=");
		} else {
			return false;
		}
	}
}
