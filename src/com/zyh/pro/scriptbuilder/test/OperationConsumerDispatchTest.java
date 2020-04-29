package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OperationConsumerDispatchTest {
	@Test
	public void dispatch() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(out));
		context.addFunction(new PrintFunction(context));
		OperationDispatcher dispatcher = new OperationDispatcher(context);

		IOperation assignOperation = dispatcher.getOperation("a=456;");
		assertTrue(assignOperation instanceof AssignOperation);
		assignOperation.execute();

		IOperation printOperation = dispatcher.getOperation("print(a);");
		System.out.println("printOperation = " + printOperation);
		assertTrue(printOperation instanceof InvokeFunctionOperation);
		printOperation.execute();

		assertThat(new String(out.toByteArray()), is("456"));
	}

	@Test
	public void getAssignDealer() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));

		OperationDispatcher dispatcher = new OperationDispatcher(context);
		OperationInterpreter operationInterpreter = dispatcher.getDealer("a=6;");
		operationInterpreter.interpret("a=6;").execute();
		assertThat(context.getVariable("a"), is("6"));
	}

	@Test
	public void getPrintDealer() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));

		OperationDispatcher dispatcher = new OperationDispatcher(context);
		OperationInterpreter operationInterpreter = dispatcher.getDealer("print(456);");
		operationInterpreter.interpret("print(456);").execute();
		assertThat(new String(output.toByteArray()), is("456"));
	}
}
