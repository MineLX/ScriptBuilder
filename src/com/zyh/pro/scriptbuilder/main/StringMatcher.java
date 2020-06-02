package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.value.StringValue;
import com.zyh.pro.scriptbuilder.main.value.Value;

public class StringMatcher implements ReturnMatcher<IValue, IStringScanner> {

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.exists("\"");
	}

	@Override
	public IValue onMatched(IStringScanner scanner) {
		char endChar = scanner.nextChar();
		return new StringValue(endChar + scanner.til(endChar) + scanner.nextChar());
	}
}
