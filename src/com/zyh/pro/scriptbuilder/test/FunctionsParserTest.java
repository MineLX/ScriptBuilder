package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.parser.FunctionsParser;
import com.zyh.pro.scriptbuilder.main.value.Value;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FunctionsParserTest {
	@Test
	public void function_with_model_params() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));

		FunctionsParser parser = new FunctionsParser(context);
		parser.onMatched(new StringScanner("function a(param){print(param);}")).execute();
		Function function = context.getFunction("a");
		function.execute(Params.of(new Value("printed ")));
		assertThat(new String(output.toByteArray()), is("printed "));
		function.execute(Params.of(new Value("printed2")));
		assertThat(new String(output.toByteArray()), is("printed printed2"));
	}

	@Test
	public void edge_parsed_remainder() {
		FunctionsParser parser = new FunctionsParser(new ScriptContext(out));
		StringScanner scanner = new StringScanner("function a(){}");
		parser.onMatched(scanner).execute();
		assertThat(scanner.toString(), is(""));
	}

	@Test
	public void simple_test() {
		ScriptContext context = new ScriptContext(null);
		FunctionsParser parser = new FunctionsParser(context);
		parser.onMatched(new StringScanner("function a(){}")).execute();
		assertNotNull(context.getFunction("a"));
	}
}
