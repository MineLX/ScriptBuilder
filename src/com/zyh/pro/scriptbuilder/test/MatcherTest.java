package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.FunctionReturnValueMatcher;
import com.zyh.pro.scriptbuilder.main.ScriptContext;
import com.zyh.pro.scriptbuilder.main.SumFunction;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MatcherTest {
	@Test
	public void simple_test() {
		ScriptContext context = new ScriptContext(System.out);
		context.addFunction(new SumFunction(context));
		FunctionReturnValueMatcher matcher2 = new FunctionReturnValueMatcher(context);
		StringScanner source = new StringScanner("sum(1,2)");
		assertThat(matcher2.isMatch(source.copy()), is(true));
		assertThat(matcher2.onMatched(source.copy()).asString(), is("3"));
	}
}
