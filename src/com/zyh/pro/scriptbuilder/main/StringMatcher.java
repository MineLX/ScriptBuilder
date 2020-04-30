package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class StringMatcher implements ReturnMatcher<String, IStringScanner> {

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.exists("\"");
	}

	@Override
	public String onMatched(IStringScanner scanner) {
		char endChar = scanner.nextChar();
		return endChar + scanner.til(endChar) + scanner.nextChar();
	}
}
