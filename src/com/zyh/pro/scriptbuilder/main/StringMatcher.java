package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnChain;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class StringMatcher implements ReturnMatcher<String, IScanner> {

	@Override
	public boolean isMatch(IScanner scanner) {
		return scanner.exists("\"");
	}

	@Override
	public String onMatched(IScanner scanner) {
		char endChar = scanner.nextChar();
		return endChar + scanner.til(endChar) + scanner.nextChar();
	}
}
