package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.zyh.pro.scriptbuilder.main.Operators.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ArithmeticSequenceTest {
	@Test
	public void sum_function_as_expression() {
		ScriptContext context = new ScriptContext(System.out);
		context.addFunction(new SumFunction(context));

		ArithmeticSequence calculator = tokenCalculator(context, asList("sum(1,2)", "+", "1"));
		assertThat(calculator.toValue().asString(), is("4"));
	}

	@Test
	public void init_builder_by_tokens() {
		ArithmeticSequence build = tokenCalculator(null, asList("1", "+", "2"));
		assertThat(build.toValue().asString(), is("3"));
		assertThat(tokenCalculator(null, Collections.emptyList()).toValue().asString(), is("0"));
	}

	@Test
	public void builder() {
		ArithmeticSequence sequence = new ArithmeticSequence()
				.plus(new Value("1"))
				.multi(new Value("2"))
				.reduce(new Value("1"));
		assertThat(sequence.toValue().asString(), is("1"));
		assertThat(new ArithmeticSequence().toValue().asString(), is("0"));
	}

	@Test
	public void toOperatorByString() {
		assertThat(ofString("+"), is(PLUS));
		assertThat(ofString("-"), is(REDUCE));
		assertThat(ofString("*"), is(MULTI));
		assertThat(ofString("/"), is(DIVIDE));
	}

	@Test
	public void swap_semantics() {
		assertThat(PLUS.changeSemanticsIfSwap(), is(false));
		assertThat(REDUCE.changeSemanticsIfSwap(), is(true));
		assertThat(MULTI.changeSemanticsIfSwap(), is(false));
		assertThat(DIVIDE.changeSemanticsIfSwap(), is(true));
	}

	@Test
	public void compareSize() {
		assertThat(PLUS.biggerThan(REDUCE), is(false));
		assertThat(REDUCE.biggerThan(PLUS), is(false));

		assertThat(MULTI.lowerThan(REDUCE), is(false));
		assertThat(MULTI.biggerThan(PLUS), is(true));

		assertThat(REDUCE.equalsThan(PLUS), is(true));

		assertThat(REDUCE.biggerEquals(PLUS), is(true));
		assertThat(PLUS.biggerEquals(REDUCE), is(true));

		assertThat(REDUCE.lowerEquals(PLUS), is(true));
		assertThat(PLUS.lowerEquals(REDUCE), is(true));
	}

	@Test
	public void mixed_multi_and_plus() {
		ArithmeticSequence calculation = tokenCalculator(null, asList("1", "+", "2", "+", "3", "*", "4"));
		assertThat(calculation.toValue().asString(), is("15"));
	}

	@Test
	public void multi() {
		ArithmeticSequence calculation = tokenCalculator(null, asList("1", "*", "2"));
		assertThat(calculation.toValue().asString(), is("2"));
	}

	@Test
	public void mixed() {
		ArithmeticSequence calculation = tokenCalculator(null, asList("1", "+", "1", "-", "1", "+", "2", "+", "4"));
		assertThat(calculation.toValue().asString(), is("7"));
	}

	@Test
	public void reduce() {
		ArithmeticSequence calculation = tokenCalculator(null, asList("1", "-", "1"));
		assertThat(calculation.toValue().asString(), is("0"));
	}

	@Test
	public void add() {
		ArithmeticSequence calculation = tokenCalculator(null, asList("1", "+", "1"));
		assertThat(calculation.toValue().asString(), is("2"));
	}

	@Test
	public void singleValue() {
		assertThat(tokenCalculator(null, singletonList("1")).toValue().asString(), is("1"));
	}

	private ArithmeticSequence tokenCalculator(ScriptContext context, List<String> tokens) {
		ArithmeticSequence sequence = new ArithmeticSequence();

		ValuesParser parser = new ValuesParser(context);

		if (tokens.isEmpty())
			return sequence;

		sequence.plus(parser.parse(new StringScanner(tokens.get(0))));
		for (int operandIndex = 1; operandIndex < tokens.size(); operandIndex += 2)
			sequence.operateWith(
					Operators.ofString(tokens.get(operandIndex)),
					parser.parse(new StringScanner(tokens.get(operandIndex + 1))));
		return sequence;
	}

	@Test
	public void zipValues() {
		ArithmeticSequence sequence = new ArithmeticSequence();
		sequence.plus(new Value("2"))
				.multi(new Value("3"));
		sequence.zipValues();
		sequence.reduce(new Value("1"));
		sequence.zipValues();

		IValue value = sequence.toValue();
		assertThat(value.asString(), is("5"));
	}

	@Test
	public void toValue() {
		ArithmeticSequence sequence = new ArithmeticSequence();
		sequence.plus(new Value("2"))
				.multi(new Value("3"))
				.reduce(new Value("1"));
		assertThat(sequence.toValue().asString(), is("5"));
	}

	@Test
	public void pop() {
		ArithmeticSequence sequence = new ArithmeticSequence();
		sequence.plus(new Value("1"))
				.plus(new Value("2"))
				.reduce(new Value("3"));
		IValue value = sequence.pop();
		assertThat(value.asString(), is("0"));
	}
}
