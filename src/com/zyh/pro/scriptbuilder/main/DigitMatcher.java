package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class DigitMatcher implements ReturnMatcher<String, IStringScanner> {

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.existsIf(Character::isDigit);
	}

	@Override
	public String onMatched(IStringScanner scanner) {
		return scanner.collect(Character::isDigit);
	}
}
