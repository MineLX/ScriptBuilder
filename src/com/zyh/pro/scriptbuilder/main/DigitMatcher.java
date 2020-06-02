package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scriptbuilder.main.value.IValue;
import com.zyh.pro.scriptbuilder.main.value.Value;

public class DigitMatcher implements ReturnMatcher<IValue, IStringScanner> {

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.existsIf(Character::isDigit);
	}

	@Override
	public IValue onMatched(IStringScanner scanner) {
		return new Value(scanner.collect(Character::isDigit));
	}
}
