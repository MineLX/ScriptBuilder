package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.CompositeMatcher;
import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;
import com.zyh.pro.scriptbuilder.main.Params;
import com.zyh.pro.scriptbuilder.main.ParamsParser;
import com.zyh.pro.scriptbuilder.main.ScriptContext;
import com.zyh.pro.scriptbuilder.main.SingleMatcher;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParamsParserTest {
//	@Test
//	public void edge_parsed_remainder() {
//		ParamsParser parser = new ParamsParser(null);
//		StringScanner scanner = new StringScanner(")");
//		parser.parse(scanner);
//		assertThat(scanner.toString(), is(")"));
//	}

	@Test
	public void edge_empty_params() {
		ScriptContext context = new ScriptContext(System.out);
		ParamsParser parser = new ParamsParser(context);
		Params parse = parser.parse(new StringScanner(")"));
		assertThat(parse.getParams().toString(), is("[]"));
	}

	@Test
	public void edge_cdata_with_comma() {
		ScriptContext context = new ScriptContext(System.out);
		ParamsParser parser = new ParamsParser(context);
		Params params = parser.parse(new StringScanner("\"hello,world\",1)"));
		assertThat(params.get(0).asString(), is("hello,world"));
		assertThat(params.get(1).asString(), is("1"));
	}

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
		Params params = parser.parse(new StringScanner("1,1+1)"));
		assertThat(params.get(0).asString(), is("1"));
		assertThat(params.get(1).asString(), is("2"));
	}
}
