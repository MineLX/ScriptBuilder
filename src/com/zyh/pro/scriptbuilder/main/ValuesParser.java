package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

import java.util.List;
import java.util.function.BinaryOperator;

import static com.zyh.pro.scanner.main.ReturnMatcher.of;

public class ValuesParser {

	private final ToResult<String, IStringScanner> operationsTokenizer;

	private final ToResult<IValue, String> singleValueGetter;

	public ValuesParser(ScriptContext context) {
		operationsTokenizer = createOperationsTokenizer();
		singleValueGetter = new CompositeToResult<IValue, String>() // FIXME 2020/5/1  wait for me!!!
				.add(of(s -> s.startsWith("\""), StringValue::new))
				.add(new FunctionReturnValueMatcher(context))
				.add(new VariableMatcher(context))
				.add(of(s -> true, Value::new));
	}

	public IValue parse(IStringScanner scanner) {
		List<String> tokens = scanner.sequence(operationsTokenizer).toList();
		if (tokens.size() == 0)
			return null;

		if (tokens.size() == 1)
			return singleValue(tokens.get(0));

		return tokensToArithmetic(tokens).toValue();
	}

	private ArithmeticSequence tokensToArithmetic(List<String> tokens) {
		ArithmeticSequence result = new ArithmeticSequence();

		if (tokens.isEmpty())
			return result;

		result.plus(parse(new StringScanner(tokens.get(0))));
		for (int operandIndex = 1; operandIndex < tokens.size(); operandIndex += 2)
			result.operateWith(
					Operators.ofString(tokens.get(operandIndex)),
					parse(new StringScanner(tokens.get(operandIndex + 1))));
		return result;
	}

	private IValue singleValue(String singleValueAsText) {
		return singleValueGetter.get(singleValueAsText);
	}

	public static ToResult<String, IStringScanner> createOperationsTokenizer() {
		BinaryOperator<String> converter = (one, another) -> one + another;
		CollectTree.Root<String, IStringScanner> result = CollectTree.root(converter);
		result.addChild(new SingleMatcher("+"));
		result.addChild(new SingleMatcher("-"));
		result.addChild(new SingleMatcher("*"));
		result.addChild(new SingleMatcher("/"));

		CollectTree<String, IStringScanner> alphaPath = new CollectTree<>(new PageMatcher());
		result.addChild(alphaPath);

		// FIXME 2020/5/2  wait for me!!!   between is unsafe   use ParamsParser instead of between
		alphaPath.addChild(new BetweenMatcher('(', ')'));

		result.addChild(new StringMatcher());
		result.addChild(new DigitMatcher());
		return result;
	}
}
