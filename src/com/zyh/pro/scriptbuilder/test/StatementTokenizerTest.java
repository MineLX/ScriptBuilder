package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.Sequence;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.StatementTokenizer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatementTokenizerTest {
	@Test
	public void multi_commands() {
		Sequence tokenizer = getSequence("print(\"string\");print(456);");
		assertThat(tokenizer.toList().toString(), is("[print, (, \"string\", ), ;, print, (, 456, ), ;]"));
	}

	@Test
	public void digits() {
		Sequence tokenizer = getSequence("print(456)");
		assertThat(tokenizer.toList().toString(), is("[print, (, 456, )]"));
	}

	@Test
	public void unordered_command() {
		Sequence tokenizer = getSequence("(\"string\"print)(");
		assertThat(tokenizer.toList().toString(), is("[(, \"string\", print, ), (]"));
	}

	@Test
	public void command_token() {
		Sequence tokenizer = getSequence("print(\"string\");");
		assertThat(tokenizer.toList().toString(), is("[print, (, \"string\", ), ;]"));
	}

	private Sequence getSequence(String source) {
		return new StringScanner(source).sequence(new StatementTokenizer().create());
	}
}
