package com.zyh.pro.scriptbuilder.main.parser;

import com.zyh.pro.scanner.main.*;
import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.Params;
import com.zyh.pro.scriptbuilder.main.ScriptContext;
import com.zyh.pro.scriptbuilder.main.SingleMatcher;

public class ParamsParser {

	private final ValuesParser valuesParser;

	private final CompositeToResult<String, IStringScanner> delimiters;

	public ParamsParser(ScriptContext context) {
		valuesParser = new ValuesParser(context);

		delimiters = new CompositeToResult<String, IStringScanner>()
				.add(new SingleMatcher(","));
	}

	// FIXME 2020/6/2  wait for me!!!
	public Params parse(IStringScanner scanner) {
		Params result = new Params();

		IValue temp;
		while ((temp = valuesParser.parse(scanner)) != null) {
			result.add(temp);
			if (delimiters.get(scanner) == null) break;
		}
		return result;
	}
}
