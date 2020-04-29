package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FunctionInvocationParserTest {
//	@Test
//	public void inner_params() {
//		ScriptContext context = new ScriptContext(System.out);
//		FunctionInvocationParser parser = new FunctionInvocationParser(context);
//		FunctionInvocation invocation = parser.parse("sum(1+1,2)");
//		assertThat(invocation.getFunctionName(), is("sum"));
//		Params params = invocation.getParams();
//		assertThat(params.get(0).asString(), is("2"));
//		assertThat(params.get(1).asString(), is("2"));
//	}

	@Test
	public void parse() {
		ScriptContext context = new ScriptContext(System.out);
		FunctionInvocationParser parser = new FunctionInvocationParser(context);
		FunctionInvocation invocation = parser.parse("sum(1,2)");
		assertThat(invocation.getFunctionName(), is("sum"));
		Params params = invocation.getParams();
		assertThat(params.get(0).asString(), is("1"));
		assertThat(params.get(1).asString(), is("2"));
	}

	@Test
	public void simple_test() {
		FunctionInvocation invocation = new FunctionInvocation("sum");
		invocation.addParam(new Value("1"));
		invocation.addParam(new Value("2"));
		Params params = invocation.getParams();
		assertThat(params.get(0).asString(), is("1"));
		assertThat(params.get(1).asString(), is("2"));
	}
}
