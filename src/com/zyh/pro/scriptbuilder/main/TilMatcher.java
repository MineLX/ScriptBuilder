package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

import static java.lang.String.valueOf;

public class TilMatcher implements ReturnMatcher<String, IStringScanner> {

	private final char tilWhat;

	public TilMatcher(char tilWhat) {
		this.tilWhat = tilWhat;
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return !scanner.exists(valueOf(tilWhat));
	}

	@Override
	public String onMatched(IStringScanner scanner) {
		return scanner.til(tilWhat);
	}
}
