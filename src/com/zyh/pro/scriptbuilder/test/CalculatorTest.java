package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.ValueCalculator.Builder;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static com.zyh.pro.scriptbuilder.main.Operators.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
	@Test
	public void sum_function_as_expression() {
		ScriptContext context = new ScriptContext(System.out);
		context.addFunction(new SumFunction(context));

		ValueCalculator calculator = tokenCalculator(context, asList("sum(1,2)", "+", "1"));
		assertThat(calculator.toValue().asString(), is("4"));
	}

	@Test
	public void init_builder_by_tokens() {
		ValueCalculator build = tokenCalculator(null, asList("1", "+", "2"));
		assertThat(build.toValue().asString(), is("3"));
		assertThat(tokenCalculator(null, Collections.emptyList()).toValue().asString(), is("0"));
	}

	@Test
	public void builder() {
		ValueCalculator calculator = new Builder()
				.plus(new Value("1"))
				.multi(new Value("2"))
				.reduce(new Value("1"))
				.build();
		assertThat(calculator.toValue().asString(), is("1"));
		assertThat(new Builder().build().toValue().asString(), is("0"));
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
		ValueCalculator calculation = tokenCalculator(null, asList("1", "+", "2", "+", "3", "*", "4"));
		assertThat(calculation.toValue().asString(), is("15"));
	}

	@Test
	public void multi() {
		ValueCalculator calculation = tokenCalculator(null, asList("1", "*", "2"));
		assertThat(calculation.toValue().asString(), is("2"));
	}

	@Test
	public void mixed() {
		ValueCalculator calculation = tokenCalculator(null, asList("1", "+", "1", "-", "1", "+", "2", "+", "4"));
		assertThat(calculation.toValue().asString(), is("7"));
	}

	@Test
	public void reduce() {
		ValueCalculator calculation = tokenCalculator(null, asList("1", "-", "1"));
		assertThat(calculation.toValue().asString(), is("0"));
	}

	@Test
	public void add() {
		ValueCalculator calculation = tokenCalculator(null, asList("1", "+", "1"));
		assertThat(calculation.toValue().asString(), is("2"));
	}

	@Test
	public void singleValue() {
		assertThat(tokenCalculator(null, singletonList("1")).toValue().asString(), is("1"));
	}


	private ValueCalculator tokenCalculator(ScriptContext context, List<String> tokens) {
		Builder builder = new Builder();

		ValueParser parser = new ValueParser(context);

		if (tokens.isEmpty())
			return builder.build();

		builder.plus(parser.parse(new StringScanner(tokens.get(0))));
		for (int operandIndex = 1; operandIndex < tokens.size(); operandIndex += 2)
			builder.operate(
					Operators.ofString(tokens.get(operandIndex)),
					parser.parse(new StringScanner(tokens.get(operandIndex + 1))));
		return builder.build();
	}
}
