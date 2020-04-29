package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

public class AnyMatcher implements ReturnMatcher<String, IScanner> {
	@Override
	public boolean isMatch(IScanner scanner) {
		return true;
	}

	@Override
	public String onMatched(IScanner scanner) {
		return "";
	}
}
