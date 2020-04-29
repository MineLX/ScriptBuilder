package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ReturnMatcher;
import com.zyh.pro.scanner.main.ReturnTree;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scanner.test.Tokenizer;

import java.util.function.BinaryOperator;

import static com.zyh.pro.scanner.main.ReturnMatcher.functional;
import static java.lang.String.valueOf;

public class ValueTokenizer extends Tokenizer {

	private final ReturnTree<String, IScanner> tokenChain;

	public ValueTokenizer(Scanner scanner) {
		super(scanner);

		tokenChain = createChain();
	}

	private ReturnTree<String, IScanner> createChain() {
		BinaryOperator<String> converter = (one, another) -> one + another;
		ReturnTree<String, IScanner> result = ReturnTree.root("", converter);
		result.addChild(new SingleMatcher("+"));
		result.addChild(new SingleMatcher("-"));
		result.addChild(new SingleMatcher("*"));
		result.addChild(new SingleMatcher("/"));

		ReturnTree<String, IScanner> alphaPath = new ReturnTree<>(new PageMatcher(), converter);
		result.addChild(alphaPath);

		alphaPath.addChild(new BetweenMatcher('(', ')'));

		result.addChild(new StringMatcher());
		result.addChild(new DigitMatcher());

		return result;
	}

	@Override
	public final String next() {
		return tokenChain.get(scanner);
	}
}
