package com.zyh.pro.scriptbuilder.main;

import java.util.List;

public class ParamsParser {

	private final ValueParser valueParser;

	public ParamsParser(ScriptContext context) {
		valueParser = new ValueParser(context);
	}

	public Params parse(String paramsAsText) {
		return getParamsFromTokens(new FunctionParamsTokenizer(paramsAsText).toList());
	}

	private Params getParamsFromTokens(List<String> tokens) {
		Params result = new Params();
		for (int i = 0; i < tokens.size(); i += 2) {
			result.add(createValue(tokens.get(i)));
		}
		return result;
	}

	private IValue createValue(String valueAsText) {
		return valueParser.parse(valueAsText);
	}
}
