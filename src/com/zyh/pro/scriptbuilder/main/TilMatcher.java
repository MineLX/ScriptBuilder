package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;

import static java.lang.String.valueOf;

public class TilMatcher implements ReturnMatcher<String, IScanner> {

	private final char tilWhat;

	public TilMatcher(char tilWhat) {
		this.tilWhat = tilWhat;
	}

	@Override
	public boolean isMatch(IScanner scanner) {
		return !scanner.exists(valueOf(tilWhat));
	}

	@Override
	public String onMatched(IScanner scanner) {
		return scanner.til(tilWhat);
	}
}
