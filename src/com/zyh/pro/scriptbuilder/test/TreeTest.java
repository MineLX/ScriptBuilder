package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scriptbuilder.main.SearchTree;
import org.junit.Test;

import static com.zyh.pro.scanner.main.Matcher.functional;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TreeTest {
	@Test
	public void double_path() {
		SearchTree.Builder<String, IScanner> builder = new SearchTree.Builder<>(
				functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha));

		SearchTree.Builder<String, IScanner>.Path alphaDigitAlphaPath =
				builder.path(functional(scanner -> scanner.existsIf(Character::isDigit), IScanner::nextFloat))
						.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha));

		alphaDigitAlphaPath.path(functional(scanner -> scanner.exists(">"), IScanner::nextChar))
				.end("Terminator", functional(scanner -> scanner.exists(";"), IScanner::nextChar));

		alphaDigitAlphaPath.end("LeftCap", functional(scanner -> scanner.exists("("), IScanner::nextChar));

		SearchTree<String, IScanner> tree = builder.build();
		assertThat(tree.search(new Scanner("alpha123alpha(")), is("LeftCap"));
		assertThat(tree.search(new Scanner("alpha123alpha>;")), is("Terminator"));
	}

	@Test
	public void builder() {
		SearchTree.Builder<String, IScanner> builder = new SearchTree.Builder<>(
				functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha));

		builder.path(functional(scanner -> scanner.existsIf(Character::isDigit), IScanner::nextFloat))
				.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha))
				.end("LeftCap", functional(scanner -> scanner.exists("("), IScanner::nextChar));

		builder.end("Terminator", functional(scanner -> scanner.exists(";"), IScanner::nextChar));

		SearchTree<String, IScanner> tree = builder.build();

		assertThat(tree.search(new Scanner("alpha123alpha(")), is("LeftCap"));
		assertThat(tree.search(new Scanner("alpha;")), is("Terminator"));
	}

	@Test
	public void simple_test() {
		SearchTree.Builder<String, IScanner> builder =
				new SearchTree.Builder<>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha));
		builder.end("Digit",
				functional(scanner -> scanner.existsIf(Character::isDigit), IScanner::nextFloat));
		builder.end("LeftCap",
				functional(scanner -> scanner.exists("("), IScanner::nextChar));
		SearchTree<String, IScanner> tree = builder.build();

		assertThat(tree.search(new Scanner("hello123")), is("Digit"));
		assertThat(tree.search(new Scanner("print(")), is("LeftCap"));
	}
}
