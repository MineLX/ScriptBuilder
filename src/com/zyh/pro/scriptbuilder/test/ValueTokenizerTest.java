package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scanner.test.Tokenizer;
import com.zyh.pro.scriptbuilder.main.ValueTokenizer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValueTokenizerTest {
	@Test
	public void reduce() {
		Tokenizer tokenizer = getTokenizer(new Scanner("1-2"));
		assertThat(tokenizer.next(), is("1"));
		assertThat(tokenizer.next(), is("-"));
		assertThat(tokenizer.next(), is("2"));
	}

	// FIXME 2020/4/28  wait for me!!!  alpha VarName test
	@Test
	public void function_involved() {
		Tokenizer tokenizer = getTokenizer(new Scanner("sum(1,2)+1"));
		assertThat(tokenizer.next(), is("sum(1,2)"));
		assertThat(tokenizer.next(), is("+"));
		assertThat(tokenizer.next(), is("1"));
	}

	@Test
	public void plus() {
		Tokenizer tokenizer = getTokenizer(new Scanner("1+2"));
		assertThat(tokenizer.next(), is("1"));
		assertThat(tokenizer.next(), is("+"));
		assertThat(tokenizer.next(), is("2"));
	}

	private Tokenizer getTokenizer(Scanner scanner) {
		return new ValueTokenizer(scanner);
	}

}
