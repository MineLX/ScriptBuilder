package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CompositeOperationTest {
	@Test
	public void simple_test() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));

		context.addFunction(new PrintFunction(context));
		CompositeOperation operation = new CompositeOperation();

		Params params = new Params();
		params.add(new Value("printed"));
		operation.add(new InvokeFunctionOperation(context, "print", params));

		operation.execute();
		assertThat(new String(output.toByteArray()), is("printed"));
	}
}
