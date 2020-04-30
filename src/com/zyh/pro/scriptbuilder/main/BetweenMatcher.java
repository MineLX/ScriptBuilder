package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class BetweenMatcher implements ReturnMatcher<String, IStringScanner> {

	private final char left;
	private final char right;

	public BetweenMatcher(char left, char right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.exists("(");
	}

	@Override
	public String onMatched(IStringScanner scanner) {
		return left + scanner.between(left, right) + right;
	}
}
