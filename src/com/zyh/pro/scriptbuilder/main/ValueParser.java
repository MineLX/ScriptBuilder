package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

import java.util.ArrayList;
import java.util.List;

import static com.zyh.pro.scanner.main.ReturnMatcher.functional;

public class ValueParser {

	private final ToResult<IValue, String> singleValueGetter;

	public ValueParser(ScriptContext context) {
		// FIXME 2020/4/29  wait for me!!!   charge to tree model
		singleValueGetter = new CompositeToResult<IValue, String>()
				.add(functional(s -> s.startsWith("\""), StringValue::new))
				.add(new FunctionInvocationMatcher(context))
				.add(new VariableMatcher(context))
				.add(functional(s -> true, Value::new));
	}

	public IValue parse(IStringScanner scanner) {
		List<String> tokens = getValueTokens(scanner);

		if (tokens.size() == 1)
			return parseSingleValue(tokens.get(0));

		return tokenCalculator(tokens).toValue();
	}

	private List<String> getValueTokens(IStringScanner scanner) {
		Sequence<String> sequence = scanner.sequence(new ValueTokenizer().create());

		List<String> tokens = new ArrayList<>();
		while (sequence.hasNext()) { // FIXME 2020/4/30  wait for me!!!  Tokenizer.til(...)
			String next = sequence.next();
			if (next == null)
				break;
			tokens.add(next);
		}
		return tokens;
	}

	// FIXME 2020/4/30  wait for me!!! Tokenizer.collect
	public String getValueAsText(IStringScanner scanner) {
		return getValueTokens(scanner).stream().reduce("", (one, another) -> one + another);
	}

	private IValue parseSingleValue(String singleValueAsText) {
		return singleValueGetter.get(singleValueAsText);
	}

	private ValueCalculator tokenCalculator(List<String> tokens) {
		ValueCalculator.Builder builder = new ValueCalculator.Builder();

		if (tokens.isEmpty())
			return builder.build();

		builder.plus(parse(new StringScanner(tokens.get(0))));
		for (int operandIndex = 1; operandIndex < tokens.size(); operandIndex += 2)
			builder.operate(
					Operators.ofString(tokens.get(operandIndex)),
					parse(new StringScanner(tokens.get(operandIndex + 1))));
		return builder.build();
	}
}
