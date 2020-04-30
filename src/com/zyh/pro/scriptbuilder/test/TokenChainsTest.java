package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.MatcherToResult;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.FunctionTokenChain;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TokenChainsTest {
	@Test
	public void simple_test() {
		MatcherToResult<String, IStringScanner> matcher =
				new MatcherToResult<>(new FunctionTokenChain());

		assertThat(matcher.get(new StringScanner("sum(1,2)")), is("sum(1,2)"));
	}
}
