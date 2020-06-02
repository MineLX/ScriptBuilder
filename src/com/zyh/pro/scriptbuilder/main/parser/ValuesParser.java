package com.zyh.pro.scriptbuilder.main.parser;

import com.zyh.pro.scanner.main.*;
import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.ArithmeticSequence.Pair;
import com.zyh.pro.scriptbuilder.main.value.IValue;

import java.util.List;

import static com.zyh.pro.scriptbuilder.main.Operators.ofString;

public class ValuesParser {

	private final CompositeToResult<IValue, IStringScanner> singleValue;

	private final PairMatcher pairParser;

	public ValuesParser(ScriptContext context) {
		singleValue = new CompositeToResult<IValue, IStringScanner>()
				.add(new StringMatcher())
				.add(new FunctionReturnValueMatcher(context))
				.add(new VariableMatcher(context))
				.add(new DigitMatcher());

		pairParser = new PairMatcher(singleValue);
	}

	public IValue parse(IStringScanner scanner) {
		if (scanner.isEmpty())
			return IValue.empty();

		IValue firstValue = singleValue.get(scanner);
		List<Pair> pairs = scanner.sequence(pairParser).toList();
		if (pairs.isEmpty())
			return firstValue;

		ArithmeticSequence result = new ArithmeticSequence();
		result.plus(firstValue);
		pairs.forEach(result::operateWith);
		return result.toValue();
	}
}
