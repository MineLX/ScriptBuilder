package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.RightValue;
import com.zyh.pro.scriptbuilder.main.ScriptContext;
import com.zyh.pro.scriptbuilder.main.SumFunction;
import org.junit.Test;

import java.beans.Expression;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RightValueTest {
	@Test
	public void simple_test() {
		ScriptContext context = new ScriptContext(System.out);
		context.addFunction(new SumFunction(context));
		RightValue value = new RightValue(context, "sum(1, 2)");
		String calculate = value.asString();
		assertThat(calculate, is("3"));
	}
}
