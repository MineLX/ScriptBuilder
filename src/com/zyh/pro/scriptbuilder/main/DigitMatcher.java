package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnChain;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class DigitMatcher implements ReturnMatcher<String, IScanner> {

	@Override
	public boolean isMatch(IScanner scanner) {
		return scanner.existsIf(Character::isDigit);
	}

	@Override
	public String onMatched(IScanner scanner) {
		return scanner.collect(Character::isDigit);
	}
}
