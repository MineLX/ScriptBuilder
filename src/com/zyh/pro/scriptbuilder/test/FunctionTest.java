package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.operation.IOperation;
import com.zyh.pro.scriptbuilder.main.operation.InvokeFunctionOperation;
import com.zyh.pro.scriptbuilder.main.operation.ReturnOperation;
import com.zyh.pro.scriptbuilder.main.value.Value;
import com.zyh.pro.scriptbuilder.main.value.VariableValue;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.zyh.pro.scriptbuilder.main.operation.IOperation.doNothing;
import static java.lang.System.out;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FunctionTest {
	@Test
	public void operations_function_return_value() {
		ScriptContext context = new ScriptContext(out);
		IOperation returnAnValue = new ReturnOperation(context, new VariableValue(context, "param"));
		OperationsFunction function = new OperationsFunction(context, "a", returnAnValue, singletonList("param"));
		assertThat(function.execute(Params.of(new Value("returnValue"))).asString(), is("returnValue"));
	}

	@Test
	public void edge_variable_scope() {
		ScriptContext context = new ScriptContext(out);
		context.addFunction(new PrintFunction(context));
		OperationsFunction function = new OperationsFunction(context, "a", doNothing(), singletonList("param"));
		context.setVariable("param", new Value("global"));
		assertThat(context.getVariable("param").asString(), is("global"));

		Params realParams = new Params();
		realParams.add(new Value("temporary"));
		function.execute(realParams);
		assertThat(context.getVariable("param").asString(), is("global"));
	}

	@Test
	public void model_params() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));

		Params params = new Params();
		params.add(new VariableValue(context, "param1"));
		InvokeFunctionOperation operation = new InvokeFunctionOperation(context, "print", params);
		OperationsFunction function = new OperationsFunction(context, "a", operation, singletonList("param1"));

		Params realParams = new Params();
		realParams.add(new Value("printed"));
		function.execute(realParams);
		assertThat(new String(output.toByteArray()), is("printed"));
	}

	@Test
	public void operations_function() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		OperationsFunction function = new OperationsFunction(context, "a", () -> context.getOutputStream().print("printed"), emptyList());
		function.execute(new Params());
		assertThat(new String(output.toByteArray()), is("printed"));
	}

	@Test
	public void sum_function() {
		SumFunction sumFunction = new SumFunction(null);
		Params params = new Params();
		params.add(new Value("1"));
		params.add(new Value("2"));
		Value value = sumFunction.execute(params);
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
			public Value execute(Params params) {
				context.getOutputStream().print(params.get(0).asString());
				return null;
			}
		};
		Params params = new Params();
		params.add(new Value("hello"));
		helloFunction.execute(params);

		assertThat(new String(output.toByteArray()), is("hello"));
	}
}
