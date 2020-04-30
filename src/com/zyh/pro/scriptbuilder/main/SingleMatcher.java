package com.zyh.pro.scriptbuilder.main;


import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

import static java.lang.String.valueOf;

public class SingleMatcher implements ReturnMatcher<String, IStringScanner> {

	private final String singleToken;

	public SingleMatcher(String singleToken) {
		this.singleToken = singleToken;
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.exists(singleToken);
	}

	@Override
	public String onMatched(IStringScanner scanner) {
		return valueOf(scanner.nextChar());
	}
}
