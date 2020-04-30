package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.Sequence;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.ValueTokenizer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValueTokenizerTest {
	@Test
	public void reduce() {
		Sequence tokenizer = getSequence(new StringScanner("1-2"));
		assertThat(tokenizer.next(), is("1"));
		assertThat(tokenizer.next(), is("-"));
		assertThat(tokenizer.next(), is("2"));
	}

	// FIXME 2020/4/28  wait for me!!!  alpha VarName test
	@Test
	public void function_involved() {
		Sequence tokenizer = getSequence(new StringScanner("sum(1,2)+1"));
		assertThat(tokenizer.next(), is("sum(1,2)"));
		assertThat(tokenizer.next(), is("+"));
		assertThat(tokenizer.next(), is("1"));
	}

	@Test
	public void plus() {
		Sequence tokenizer = getSequence(new StringScanner("1+2"));
		assertThat(tokenizer.next(), is("1"));
		assertThat(tokenizer.next(), is("+"));
		assertThat(tokenizer.next(), is("2"));
	}

	private Sequence getSequence(StringScanner scanner) {
		return scanner.sequence(new ValueTokenizer().create());
	}
}
