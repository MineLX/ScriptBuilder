package com.zyh.pro.scriptbuilder.main;

import java.util.Stack;

import static com.zyh.pro.scriptbuilder.main.Operators.*;

public class ArithmeticSequence {

	private final Stack<Operators> operators;

	private final Stack<IValue> values;

	public ArithmeticSequence() {
		operators = new Stack<>();
		values = new Stack<>();
		values.push(new Value("0"));
	}

	public ArithmeticSequence plus(IValue value) {
		operateWith(PLUS, value);
		return this;
	}

	public ArithmeticSequence reduce(IValue value) {
		operateWith(REDUCE, value);
		return this;
	}

	public ArithmeticSequence multi(IValue value) {
		operateWith(MULTI, value);
		return this;
	}

	public ArithmeticSequence operateWith(Operators operator, IValue value) {
		Operators prev = prevPushedOperator();
		if (operator.lowerEquals(prev))
			zipValues();

		operators.add(operator);
		values.add(value);
		return this;
	}

	private Operators prevPushedOperator() {
		if (operators.isEmpty())
			return UNKNOWN;
		return operators.peek();
	}

	public IValue pop() {
		IValue firstPopped = values.pop();
		IValue secondPopped = values.pop();
		IValue result = operators.pop().calculate(secondPopped, firstPopped);
		return values.push(result);
	}

	public IValue toValue() {
		zipValues();
		return values.peek();
	}

	public void zipValues() {
		while (!operators.isEmpty())
			pop();
	}
}
