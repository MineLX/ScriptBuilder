package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.Params;
import com.zyh.pro.scriptbuilder.main.ParamsParser;
import com.zyh.pro.scriptbuilder.main.ScriptContext;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParamsParserTest {
//	@Test
//	public void cdata_with_comma() {
//		ScriptContext context = new ScriptContext(System.out);
//		ParamsParser parser = new ParamsParser(context);
//		Params params = parser.parse("\"hello,world\",1");
//		assertThat(params.get(0).asString(), is("hello,world"));
//		assertThat(params.get(1).asString(), is("1"));
//	}

	@Test
	public void parse_params_levelly() {
		ScriptContext context = new ScriptContext(System.out);
		ParamsParser parser = new ParamsParser(context);
		StringScanner scanner = new StringScanner("sum(1,1+1)");
		scanner.nextPage();
		scanner.nextChar();
		Params params = parser.parse(scanner);
		assertThat(params.get(0).asString(), is("1"));
		assertThat(params.get(1).asString(), is("2"));
	}

	@Test
	public void inner_params() {
		ScriptContext context = new ScriptContext(System.out);
		ParamsParser parser = new ParamsParser(context);
		Params params = parser.parse("1,1+1");
		assertThat(params.get(0).asString(), is("1"));
		assertThat(params.get(1).asString(), is("2"));
	}
}
