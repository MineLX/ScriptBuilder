package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.value.Value;
import org.junit.Test;

import static com.zyh.pro.scriptbuilder.main.Params.of;
import static java.lang.System.out;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ScriptContextTest {
	@Test
	public void returnValue() {
		ScriptContext context = new ScriptContext(out);
		context.pushFunctionFrame(emptyList(), of());
		context.getFunctionFrame().setReturnValue(new Value("returnValue"));
		assertThat(context.getFunctionFrame().getReturnValue().asString(), is("returnValue"));
		assertThat(context.popFunctionFrame().getReturnValue().asString(), is("returnValue"));
		assertNull(context.getFunctionFrame());
	}

	@Test
	public void getVariable() {
		ScriptContext context = new ScriptContext(out);
		context.setVariable("a", new Value("6"));
		IValue value = context.getVariable("a");
		assertThat(value.asString(), is("6"));
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
