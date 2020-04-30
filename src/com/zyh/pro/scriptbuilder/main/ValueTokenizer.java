package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.*;

import java.util.function.BinaryOperator;

public class ValueTokenizer {

	public ToResult<String, IStringScanner> create() {
		BinaryOperator<String> converter = (one, another) -> one + another;
		ReturnTree.Root<String, IStringScanner> result = ReturnTree.root(converter);
		result.addChild(new SingleMatcher("+"));
		result.addChild(new SingleMatcher("-"));
		result.addChild(new SingleMatcher("*"));
		result.addChild(new SingleMatcher("/"));
//		result.addChild(new SingleMatcher(")"));

		ReturnTree<String, IStringScanner> alphaPath = new ReturnTree<>(new PageMatcher());
		result.addChild(alphaPath);

		alphaPath.addChild(new BetweenMatcher('(', ')'));

		result.addChild(new StringMatcher());
		result.addChild(new DigitMatcher());

		return result;
	}
}
