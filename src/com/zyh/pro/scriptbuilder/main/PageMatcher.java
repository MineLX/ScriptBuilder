package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnChain;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class PageMatcher implements ReturnMatcher<String, IScanner> {

	@Override
	public boolean isMatch(IScanner scanner) {
		return scanner.existsIf(Character::isAlphabetic);
	}

	@Override
	public String onMatched(IScanner scanner) {
		return scanner.nextPage();
	}
}