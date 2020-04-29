package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.test.Tokenizer;
import com.zyh.pro.scriptbuilder.main.StatementTokenizer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatementTokenizerTest {
	@Test
	public void multi_commands() {
		StatementTokenizer tokenizer = new StatementTokenizer("print(\"string\");print(456);");
		assertThat(tokenizer.toList().toString(), is("[print, (, \"string\", ), ;, print, (, 456, ), ;]"));
	}

	@Test
	public void digits() {
		StatementTokenizer tokenizer = new StatementTokenizer("print(456)");
		assertThat(tokenizer.toList().toString(), is("[print, (, 456, )]"));
	}

	@Test
	public void unordered_command() {
		StatementTokenizer tokenizer = new StatementTokenizer("(\"string\"print)(");
		assertThat(tokenizer.toList().toString(), is("[(, \"string\", print, ), (]"));
	}

	@Test
	public void command_token() {
		Tokenizer tokenizer = new StatementTokenizer("print(\"string\");");
		assertThat(tokenizer.toList().toString(), is("[print, (, \"string\", ), ;]"));
	}
}
