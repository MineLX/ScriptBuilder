package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.ScriptInterpreter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScriptInterpreterTest {
	@Test
	public void structure() {
		TestHelper helper = new TestHelper();

	}

	@Test
	public void function_with_return_value() {
		TestHelper helper = new TestHelper();
		helper.execute("function a(){return 6;}");
		helper.assertOutput("print(a());", "6");
	}

	@Test
	public void function_with_model_params() {
		TestHelper helper = new TestHelper();
		helper.execute("print(\"hello\");function printWith(item){print(item);}");
		helper.assertOutput("printWith(123);", "hello123");
	}

	@Test
	public void invoke_declared_function() {
		TestHelper helper = new TestHelper();
		helper.assertOutput("function print123(){print(123);}print123();", "123");
	}

	@Test
	public void function_declaration() {
		TestHelper helper = new TestHelper();
		helper.execute("function print123(){print(123);}");
		helper.assertOutput("print123();", "123");
	}

	@Test
	public void print_sum_expr() {
		TestHelper helper = new TestHelper();
		helper.assertOutput("print(sum(1,sum(1, sum(1,1))));", "4");
	}

	@Test
	public void inner_sum() {
		TestHelper helper = new TestHelper();
		helper.execute("a=sum(sum(1,2), 2);");
		helper.assertOutput("print(a);", "5");
	}

	@Test
	public void sum() {
		TestHelper helper = new TestHelper();
		helper.execute("a = sum(1, 2);");
		helper.assertOutput("print(a);", "3");
	}

	@Test
	public void assign() {
		TestHelper helper = new TestHelper();
		helper.execute("a = 6;");
		helper.assertOutput("print(a);", "6");
	}

	@Test
	public void edge_statement_with_space() {
		TestHelper helper = new TestHelper();
		helper.assertOutput("  print   ( 456 ) ;", "456");
	}

	@Test
	public void edge_statement_multi_with_cdata() {
		TestHelper helper = new TestHelper();
		helper.assertOutput("print(\";\");print(1);", ";1");
	}

	@Test
	public void statement_multi() {
		TestHelper helper = new TestHelper();
		helper.assertOutput("print(\"hello world\");print(456);", "hello world456");
	}

	@Test
	public void statement_print() {
		TestHelper helper = new TestHelper();
		helper.assertOutput("print(\"hello world\");", "hello world");
		helper.assertOutput("print(456);", "hello world456");
	}

	private static class TestHelper {

		private final ByteArrayOutputStream outputStream;

		private final ScriptInterpreter interpreter;

		private TestHelper() {
			outputStream = new ByteArrayOutputStream();
			interpreter = new ScriptInterpreter(new PrintStream(outputStream));
		}

		private void execute(String target) {
			interpreter.interpret(target).execute();
		}

		private void assertOutput(String command, String expected) {
			interpreter.interpret(command).execute();
			assertThat(toString(outputStream), is(expected));
		}

		private String toString(ByteArrayOutputStream outputStream) {
			return new String(outputStream.toByteArray());
		}
	}
}
