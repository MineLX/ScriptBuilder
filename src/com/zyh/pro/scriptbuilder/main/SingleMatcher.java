package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

import static java.lang.String.valueOf;

public class SingleMatcher implements ReturnMatcher<String, IScanner> {

	private final String singleToken;

	public SingleMatcher(String singleToken) {
		this.singleToken = singleToken;
	}

	@Override
	public boolean isMatch(IScanner scanner) {
		return scanner.exists(singleToken);
	}

	@Override
	public String onMatched(IScanner scanner) {
		return valueOf(scanner.nextChar());
	}
}
