package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.FunctionParamsTokenizer;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FunctionParamsTokenizerTest {
	@Test
	public void arithmetic() {
		FunctionParamsTokenizer tokenizer = new FunctionParamsTokenizer("1,1+1");
		assertThat(tokenizer.next(), is("1"));
		assertThat(tokenizer.next(), is(","));
		assertThat(tokenizer.next(), is("1+1"));
	}

	@Test
	public void simple_test() {
		FunctionParamsTokenizer tokenizer = new FunctionParamsTokenizer("1, 2, 3, ");
		List<String> tokens = tokenizer.toList();
		assertThat(tokens.toString(), is("[1, ,, 2, ,, 3, ,]"));
	}
}
