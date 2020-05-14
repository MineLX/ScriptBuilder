package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

public class ParamsParser {

	private final ValuesParser valuesParser;

	private final CompositeToResult<String, IStringScanner> delimiters2;

	public ParamsParser(ScriptContext context) {
		valuesParser = new ValuesParser(context);

		delimiters2 = new CompositeToResult<String, IStringScanner>()
				.add(new SingleMatcher(","));
	}

	public Params parse(IStringScanner scanner) {
		Params result = new Params();

		IValue temp;
		while ((temp = valuesParser.parse(scanner)) != null) {
			result.add(temp);
			if (delimiters2.get(scanner) == null)
				break;
		}
		return result;
	}
}
