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

		private final ValueParser parser;

		public Builder(ScriptContext context) {
			operators = new ArrayList<>();
			values = new ArrayList<>();
			parser = new ValueParser(context);

			values.add(new Value("0"));
		}

		public Builder plus(String expression) {
			operateWith(PLUS, expression);
			return this;
		}

		public Builder reduce(String expression) {
			operateWith(REDUCE, expression);
			return this;
		}

		public Builder multi(String expression) {
			operateWith(MULTI, expression);
			return this;
		}

		private void operateWith(Operators operator, String expression) {
			operators.add(operator);
			values.add(parser.parse(expression));
		}

		public ValueCalculator build() {
			return new ValueCalculator(operators, values);
		}

		public Builder tokens(List<String> tokens) {
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
}
