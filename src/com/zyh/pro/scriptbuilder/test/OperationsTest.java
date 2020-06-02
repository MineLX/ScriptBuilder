package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.operation.AssignOperation;
import com.zyh.pro.scriptbuilder.main.operation.InvokeFunctionOperation;
import com.zyh.pro.scriptbuilder.main.operation.ReturnOperation;
import com.zyh.pro.scriptbuilder.main.value.Value;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OperationsTest {
	@Test
	public void return_operation() {
		ScriptContext context = new ScriptContext(System.out);
		context.pushFunctionFrame(Collections.emptyList(), Params.of());
		new ReturnOperation(context, new Value("returnValue")).execute();
		assertThat(context.popFunctionFrame().getReturnValue().asString(), is("returnValue"));
	}

	@Test
	public void assign_operation() {
		ScriptContext context = new ScriptContext(System.out);
		AssignOperation operation = new AssignOperation(context, "a", new Value("6"));
		operation.execute();
		assertThat(context.getVariable("a").asString(), is("6"));
	}

	@Test
	public void simple_test() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));

		Params params = new Params();
		params.add(new Value("printed"));
		InvokeFunctionOperation operation = new InvokeFunctionOperation(context, "print", params);
		operation.execute();
		assertThat(new String(output.toByteArray()), is("printed"));
	}
}
