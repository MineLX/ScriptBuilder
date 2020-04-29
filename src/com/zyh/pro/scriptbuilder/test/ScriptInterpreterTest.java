package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.ScriptInterpreter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScriptInterpreterTest {
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
	public void edge_statement_with_no_delimiter() {
		TestHelper helper = new TestHelper();
		helper.assertOutput("print(456);", "456");
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
			this.outputStream = new ByteArrayOutputStream();
			this.interpreter = new ScriptInterpreter(new PrintStream(outputStream));
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
