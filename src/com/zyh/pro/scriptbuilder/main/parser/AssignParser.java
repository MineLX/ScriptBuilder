package com.zyh.pro.scriptbuilder.main.parser;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scriptbuilder.main.operation.AssignOperation;
import com.zyh.pro.scriptbuilder.main.operation.IOperation;
import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.ScriptContext;

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
