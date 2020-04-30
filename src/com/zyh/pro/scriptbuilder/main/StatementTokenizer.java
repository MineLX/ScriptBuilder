package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

import static java.lang.String.valueOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatementTokenizer {
	public ToResult<String, IStringScanner> create() {
		return new CompositeToResult<String, IStringScanner>()
				.add(new MatcherToResult<>(new PageMatcher()))
				.add(new MatcherToResult<>(new SingleMatcher("(")))
				.add(new MatcherToResult<>(new SingleMatcher(")")))
				.add(new MatcherToResult<>(new SingleMatcher(";")))
				.add(new MatcherToResult<>(new SingleMatcher("=")))
				.add(new MatcherToResult<>(new SingleMatcher(",")))
				.add(new MatcherToResult<>(new SingleMatcher("-")))
				.add(new MatcherToResult<>(new SingleMatcher("+")))
				.add(new MatcherToResult<>(new SingleMatcher("*")))
				.add(new MatcherToResult<>(new SingleMatcher("/")))
				.add(new MatcherToResult<>(new StringMatcher()))
				.add(new MatcherToResult<>(new DigitMatcher()));
	}
}
