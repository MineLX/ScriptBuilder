package com.zyh.pro.scriptbuilder.main;

import java.util.List;

import static com.zyh.pro.scriptbuilder.main.Operators.*;

public class ArithmeticSequence {

	private final List<Operators> operators;

	private final List<String> numbers;

	public ArithmeticSequence(List<Operators> operators, List<String> numbers) {
		this.operators = operators;
		this.numbers = numbers;
	}

	public ArithmeticSequence plus(String expression) {
		operateWith(PLUS, expression);
		return this;
	}

	public ArithmeticSequence reduce(String expression) {
		operateWith(REDUCE, expression);
		return this;
	}

	public ArithmeticSequence multi(String expression) {
		operateWith(MULTI, expression);
		return this;
	}

	public ArithmeticSequence operateWith(Operators operator, String expression) {
		operators.add(operator);
		numbers.add(expression);
		return this;
	}
	// FIXME 2020/4/28  wait for me!!!  pushValue
	public ArithmeticSequence tokens(List<String> tokens) {
		if (tokens.isEmpty())
			return this;

		plus(tokens.get(0));
		for (int operandIndex = 1; operandIndex < tokens.size(); operandIndex += 2)
			operateWith(
					Operators.ofString(tokens.get(operandIndex)),
					tokens.get(operandIndex + 1));
		return this;
	}
}
