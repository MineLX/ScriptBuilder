package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.CompositeToResult;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scanner.main.ToResult;

import java.util.List;

import static com.zyh.pro.scanner.main.ReturnMatcher.functional;

public class ValueParser {

	private final ScriptContext context;

	private final ToResult<IValue, String> singleValueGetter;

	public ValueParser(ScriptContext context) {
		this.context = context;

		singleValueGetter = new CompositeToResult<IValue, String>()
				.add(functional(s -> s.startsWith("\""), StringValue::new))
				.add(new FunctionInvocationMatcher(context))
				.add(new VariableMatcher(context))
				.add(functional(s -> true, Value::new));
	}

	public IValue parse(String expression) {
		List<String> tokens = new ValueTokenizer(new Scanner(expression)).toList();

		if (tokens.size() == 1)
			return singleValueGetter.get(tokens.get(0));

		ValueCalculator calculator = new ValueCalculator.Builder(context).tokens(tokens).build();
		return calculator.toValue();
	}
}
