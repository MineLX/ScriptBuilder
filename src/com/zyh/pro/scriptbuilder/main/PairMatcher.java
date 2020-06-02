package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.ToResult;
import com.zyh.pro.scriptbuilder.main.value.IValue;

public class PairMatcher implements ReturnMatcher<ArithmeticSequence.Pair, IStringScanner> {
	private final ToResult<IValue, IStringScanner> singleValue;

	public PairMatcher(ToResult<IValue, IStringScanner> singleValue) {
		this.singleValue = singleValue;
	}

	@Override
	public ArithmeticSequence.Pair onMatched(IStringScanner scanner) {
		return new ArithmeticSequence.Pair(
				Operators.ofString(String.valueOf(scanner.nextChar())), singleValue.get(scanner));
	}

	@Override
	public boolean isMatch(IStringScanner scanner) {
		return scanner.exists("+") || scanner.exists("-") || scanner.exists("*") || scanner.exists("/");
	}
}
