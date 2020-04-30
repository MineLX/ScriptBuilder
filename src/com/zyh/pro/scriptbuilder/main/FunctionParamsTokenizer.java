package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.CompositeToResult;
import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ToResult;

public class FunctionParamsTokenizer {

	public ToResult<String, IStringScanner> create() {
		return new CompositeToResult<String, IStringScanner>()
				.add(new TilMatcher(','))
				.add(new SingleMatcher(","));
	}
}
