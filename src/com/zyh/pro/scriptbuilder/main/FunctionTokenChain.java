package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class FunctionTokenChain implements ReturnMatcher<String, IStringScanner> {
	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.existsIf(Character::isAlphabetic);
	}

	@Override
	public String onMatched(IStringScanner scanner) {
		return scanner.nextPage() +
				'(' +
				scanner.between('(', ')') +
				')';
	}
}
