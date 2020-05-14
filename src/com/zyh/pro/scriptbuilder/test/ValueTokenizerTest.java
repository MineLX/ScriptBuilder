package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.Sequence;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.ValuesParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValueTokenizerTest {
	@Test
	public void reduce() {
		Sequence tokenizer = getSequence(new StringScanner("1-2"));
		assertThat(tokenizer.toList().toString(), is("[1, -, 2]"));
	}

	@Test
	public void function_involved() {
		Sequence tokenizer = getSequence(new StringScanner("sum(1,2)+1"));
		assertThat(tokenizer.toList().toString(), is("[sum(1,2), +, 1]"));
	}

	@Test
	public void plus() {
		Sequence tokenizer = getSequence(new StringScanner("1+2"));
		assertThat(tokenizer.toList().toString(), is("[1, +, 2]"));
	}

	private static Sequence getSequence(StringScanner scanner) {
		return scanner.sequence(ValuesParser.createOperationsTokenizer());
	}
}
