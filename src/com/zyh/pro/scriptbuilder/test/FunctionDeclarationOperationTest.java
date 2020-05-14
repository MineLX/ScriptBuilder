package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FunctionDeclarationOperationTest {
	@Test
	public void declaration_with_modelParams() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(output);
		ScriptContext context = new ScriptContext(out);
		context.addFunction(new PrintFunction(context));
		Params invokerParams = Params.of(new VariableValue(context, "param"));
		InvokeFunctionOperation operation = new InvokeFunctionOperation(context, "print", invokerParams);
		new FunctionDeclareOperation(context, "a", operation, singletonList("param")).execute();
		Function function = context.getFunction("a");
		function.execute(Params.of(new Value("printed")));
		assertThat(new String(output.toByteArray()), is("printed"));
	}

	@Test
	public void simple_test() {
		ScriptContext context = new ScriptContext(System.out);
		IOperation operation = new CompositeOperation();
		new FunctionDeclareOperation(context, "a", operation, emptyList()).execute();
		assertNotNull(context.getFunction("a"));
	}
}
