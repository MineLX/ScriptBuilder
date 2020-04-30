package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;
import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValueParserTest {
	@Test
	public void end_parser() {
		ValueParser parser = new ValueParser(null);
		StringScanner scanner = new StringScanner("1+1)");
		IValue parse = parser.parse(scanner);
		assertThat(parse.asString(), is("2"));
		assertThat(scanner.toString(), is(")")); // FIXME 2020/4/30  wait for me!!!  verify it
	}

	@Test
	public void edge_cdata_contains_leftCap() {
		ValueParser parser = new ValueParser(null);
		assertThat(parser.parse(new StringScanner("\"(string)\"")).asString(), is("(string)"));
	}

	@Test
	public void variable_value() {
		ScriptContext context = new ScriptContext(System.out);
		context.setVariable("a", "6");
		ValueParser parser = new ValueParser(context);
		assertThat(parser.parse(new StringScanner("a")).asString(), is("6"));
	}

	@Test
	public void string_value() {
		ValueParser parser = new ValueParser(null);
		assertThat(parser.parse(new StringScanner("\";\"")).asString(), is(";"));
	}

	@Test
	public void parse_sum_function() {
		ScriptContext context = new ScriptContext(System.out);
		context.addFunction(new SumFunction(context));
		ValueParser parser = new ValueParser(context);
		assertThat(parser.parse(new TrimmedStringScanner(new StringScanner("sum(1, 2)"))).asString(), is("3"));
	}

	@Test
	public void functionValue() {
		ScriptContext context = new ScriptContext(System.out);
		context.addFunction(new SumFunction(context));

		Params params = new Params();
		params.add(new Value("1"));
		params.add(new Value("2"));
		FunctionValue functionValue = new FunctionValue(context, "sum", params);
		assertThat(functionValue.asString(), is("3"));
	}

	@Test
	public void composite_expr() {
		assertThat(new ValueParser(null).parse(new StringScanner("1+2+1*3-5")).asString(), is("1"));
	}

	@Test
	public void reduce() {
		assertThat(new ValueParser(null).parse(new StringScanner("4-3")).asString(), is("1"));
	}

	@Test
	public void multiOperator() {
		assertThat(Operator.multi(new Value("1"), new Value("2")).asString(), is("2"));
	}

	@Test
	public void reduceOperator() {
		assertThat(Operator.reduce(new Value("1"), new Value("2")).asString(), is("-1"));
	}

	@Test
	public void plusOperator() {
		assertThat(Operator.plus(new Value("1"), new Value("2")).asString(), is("3"));
	}

	@Test
	public void plus() {
		IValue value = new ValueParser(null).parse(new StringScanner("1+2"));
		assertThat(value.asString(), is("3"));
	}

	@Test
	public void parse() {
		IValue value = new ValueParser(null).parse(new StringScanner("3"));
		assertThat(value.asString(), is("3"));
	}
}
