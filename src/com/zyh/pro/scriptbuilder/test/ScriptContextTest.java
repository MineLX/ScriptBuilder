package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.Function;
import com.zyh.pro.scriptbuilder.main.ScriptContext;
import com.zyh.pro.scriptbuilder.main.SumFunction;
import org.junit.Test;

import java.util.ArrayList;

import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScriptContextTest {
	@Test
	public void calculateRightValue() {
		ScriptContext context = new ScriptContext(out);
		context.addFunction(new SumFunction(context));
		assertThat(context.calculateRightValue("sum(1,2)"), is("3"));
		assertThat(context.calculateRightValue("1 + 2"), is("3"));
	}

	@Test
	public void getVariable() {
		ScriptContext context = new ScriptContext(out);
		context.setVariable("a", "6");
		String value = context.getVariable("a");
		assertThat(value, is("6"));
	}

	@Test
	public void function_support() {
		ScriptContext context = new ScriptContext(out);
		Function addend = new Function(context, "name");
		context.addFunction(addend);
		assertThat(context.getFunction("name"), is(addend));
	}

	@Test
	public void simple_test() {
		ScriptContext context = new ScriptContext(out);
		assertThat(context.getOutputStream(), is(out));
	}
}
