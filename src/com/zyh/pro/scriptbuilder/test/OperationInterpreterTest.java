package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OperationInterpreterTest {
	@Test
	public void assign() {
		ScriptContext context = new ScriptContext(out);
		AssignInterpreter dealer = new AssignInterpreter(context);
		dealer.interpret("a=6;").execute();
		assertThat(context.getVariable("a"), is("6"));
	}

	@Test
	public void simple_test() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));
		InvokeFunctionInterpreter dealer = new InvokeFunctionInterpreter(context);
		dealer.interpret("print(456)").execute();
		assertThat(new String(output.toByteArray()), is("456"));
	}
}
