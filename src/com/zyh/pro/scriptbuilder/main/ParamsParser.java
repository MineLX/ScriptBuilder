package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

import java.util.List;
import java.util.function.BinaryOperator;

import static com.zyh.pro.scanner.main.ReturnMatcher.functional;

public class ParamsParser {

	private final ValueParser valueParser;
	private final ReturnTree.Root<String, IStringScanner> valueMatcher;

	public ParamsParser(ScriptContext context) {
		valueParser = new ValueParser(context);

		BinaryOperator<String> converter = (one, another) -> one + another;
		valueMatcher = ReturnTree.root(converter);
		valueMatcher.addChild(new SingleMatcher("+"));
		valueMatcher.addChild(new SingleMatcher("-"));
		valueMatcher.addChild(new SingleMatcher("*"));
		valueMatcher.addChild(new SingleMatcher("/"));
		valueMatcher.addChild(new SingleMatcher(")"));

		ReturnTree<String, IStringScanner> alphaPath = new ReturnTree<>(new PageMatcher());
		valueMatcher.addChild(alphaPath);

		alphaPath.addChild(new BetweenMatcher('(', ')'));

		valueMatcher.addChild(new StringMatcher());
		valueMatcher.addChild(new DigitMatcher());

	}

	public Params parse(String paramsAsText) {
		FunctionParamsTokenizer tokenizer = new FunctionParamsTokenizer();
		Sequence<String> sequence = new TrimmedStringScanner(new StringScanner(paramsAsText)).sequence(tokenizer.create());
		return getParamsFromTokens(sequence.toList());
	}

	private Params getParamsFromTokens(List<String> tokens) {
		Params result = new Params();
		for (int i = 0; i < tokens.size(); i += 2) {
			result.add(createValue(tokens.get(i)));
		}
		return result;
	}

	private IValue createValue(String valueAsText) {
		return valueParser.parse(new StringScanner(valueAsText));
	}

	public Params parse(IStringScanner scanner) {
		System.out.println("scanner = " + scanner);

		CompositeToResult<String, IStringScanner> add = new CompositeToResult<String, IStringScanner>()
				.add(new SingleMatcher(","))
				.add(new SingleMatcher(")"))
				.add(functional(scanner1 -> true, valueParser::getValueAsText));

		Params params = new Params();
		String token = add.get(scanner);
		while (!token.equals(")")) {
			System.out.println("token!!!! = " + token);
			if (token.equals(",")) {
				token = add.get(scanner);
				continue;
			}
			params.add(valueParser.parse(new StringScanner(token)));
			token = add.get(scanner);
		}
		return params;
	}
}
