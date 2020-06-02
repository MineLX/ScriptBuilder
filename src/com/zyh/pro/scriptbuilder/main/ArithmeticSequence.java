package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.value.Value;

import java.util.Stack;
import java.util.stream.Collectors;

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
		return operateWith(PLUS, value);
	}

	public ArithmeticSequence reduce(IValue value) {
		return operateWith(REDUCE, value);
	}

	public ArithmeticSequence multi(IValue value) {
		return operateWith(MULTI, value);
	}

	public ArithmeticSequence operateWith(Operators operator, IValue value) {
		Operators prev = prevPushedOperator();
		if (operator.lowerEquals(prev))
			zipValues();

		operators.add(operator);
		values.add(value);
		return this;
	}

	public ArithmeticSequence operateWith(Pair pair) {
		// FIXME 2020/6/2  wait for me!!!  remove .get
		return operateWith(pair.getOperand(), pair.getValue());
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

	public static class Pair {
		private final Operators operator;
		private final IValue value;

		public Pair(Operators operator, IValue value) {
			this.operator = operator;
			this.value = value;
		}

		public Operators getOperand() {
			return operator;
		}

		public IValue getValue() {
			return value;
		}

		@Override
		public String toString() {
			// FIXME 2020/6/2  wait for me!!!
			return "Pair(" + operator + ", " + value.asString() + ")";
		}
	}
}
