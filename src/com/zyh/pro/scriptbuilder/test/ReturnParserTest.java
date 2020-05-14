package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import java.util.Collections;

import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReturnParserTest {
	@Test
	public void simple_test() {
		ScriptContext context = new ScriptContext(out);
		context.pushFunctionFrame(Collections.emptyList(), Params.of());
		new ReturnParser(context).get(new StringScanner("return \"returnValue\";")).execute();
		assertThat(context.popFunctionFrame().getReturnValue().asString(), is("returnValue"));
	}
}
