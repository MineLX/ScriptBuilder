package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

import static java.lang.String.valueOf;

public class FunctionParamsTokenizer extends ChainTokenizer {

	public FunctionParamsTokenizer(String source) {
		super(new Scanner(source));
	}

	@Override
	protected ToResult<String, IScanner> create() {
		return new CompositeToResult<String, IScanner>()
				.add(new TilMatcher(','))
				.add(new SingleMatcher(","));
	}
}
