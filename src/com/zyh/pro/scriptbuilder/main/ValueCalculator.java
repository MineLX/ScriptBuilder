package com.zyh.pro.scriptbuilder.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.zyh.pro.scriptbuilder.main.Operators.*;

public class ValueCalculator {

	private final List<Operators> allOperators;

	private final List<IValue> allValues;

	private Stack<Operators> operators;

	private Stack<IValue> values;

	private int numberIndex;

	private ValueCalculator(List<Operators> allOperators, List<IValue> allValues) {
		this.allOperators = allOperators;
		this.allValues = allValues;
		operators = new Stack<>();
		this.values = new Stack<>();
	}

	public IValue toValue() {
		pushExpression(PLUS, new Value("0"));

		values.push(nextValue());
		for (Operators currentOperand : allOperators) {
			if (currentOperand.lowerEquals(prevOperand()))
				zipValues();
			pushExpression(currentOperand, nextValue());
		}

		// end calculating
		if (values.size() != 1)
			zipValues();
		return values.peek();
	}

	private IValue nextValue() {
		return allValues.get(numberIndex++);
	}

	// FIXME 2020/4/29  wait for me!!!  Arithmetic Sequence
	private void pushExpression(Operators operator, IValue value) {
		values.push(value);
		operators.push(operator);
	}

	private Operators prevOperand() {
		return operators.get(operators.size() - 1);
	}

	private void zipValues() {
		IValue result = values.pop();
		while (!values.isEmpty()) {
			Operators operand = operators.pop();

			if (operand.changeSemanticsIfSwap()) {
				result = operand.calculate(values.pop(), result);
			} else {
				result = operand.calculate(result, values.pop());
			}
		}
		values.push(result);
	}

	public static class Builder {

		private final List<IValue> values;

		private final List<Operators> operators;

		public Builder() {
			operators = new ArrayList<>();
			values = new ArrayList<>();

			values.add(new Value("0"));
		}

		public Builder plus(IValue value) {
			operate(PLUS, value);
			return this;
		}

		public Builder reduce(IValue value) {
			operate(REDUCE, value);
			return this;
		}

		public Builder multi(IValue value) {
			operate(MULTI, value);
			return this;
		}

		public void operate(Operators operator, IValue value) {
			operators.add(operator);
			values.add(value);
		}

		public ValueCalculator build() {
			return new ValueCalculator(operators, values);
		}
	}
}
