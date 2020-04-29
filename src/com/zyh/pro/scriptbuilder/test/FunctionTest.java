package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FunctionTest {

	@Test
	public void sum_function() {
		SumFunction sumFunction = new SumFunction(null);
		Params params = new Params();
		params.add(new Value("1"));
		params.add(new Value("2"));
		ReturnValue value = sumFunction.execute(params);
		assertThat(value.asString(), is("3"));
	}

	@Test
	public void print_function() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));

		PrintFunction printFunction = new PrintFunction(context);
		Params params = new Params();
		params.add(new Value("456"));
		printFunction.execute(params);
		assertThat(new String(output.toByteArray()), is("456"));
	}

	@Test
	public void function() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		ScriptContext context = new ScriptContext(new PrintStream(output));
		Function helloFunction = new Function(context, "print") {
			@Override
			public ReturnValue execute(Params params) {
				context.getOutputStream().print(params.get(0).asString());
				return super.execute(params);
			}
		};
		Params params = new Params();
		params.add(new Value("hello"));
		helloFunction.execute(params);

		assertThat(new String(output.toByteArray()), is("hello"));
	}
}
