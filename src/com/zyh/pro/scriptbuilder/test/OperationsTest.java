package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OperationsTest {
	@Test
	public void assign_operation() {
		ScriptContext context = new ScriptContext(System.out);
		AssignOperation operation = new AssignOperation(context, "a", new RightValue(context, "6"));
		operation.execute();
		assertThat(context.getVariable("a"), is("6"));
	}

	@Test
	public void simple_test() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));

		Params params = new Params();
		params.add(new Value("printed"));
		InvokeFunctionOperation operation = new InvokeFunctionOperation(context.getFunction("print"), params);
		operation.execute();
		assertThat(new String(output.toByteArray()), is("printed"));
	}
}
