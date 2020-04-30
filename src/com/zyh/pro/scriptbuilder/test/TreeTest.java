package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.SearchTree;
import org.junit.Test;

import static com.zyh.pro.scanner.main.Matcher.functional;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TreeTest {
	@Test
	public void double_path() {
		SearchTree.Builder<String, IStringScanner> builder = new SearchTree.Builder<>(
				functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		SearchTree.Builder<String, IStringScanner>.Path alphaDigitAlphaPath =
				builder.path(functional(scanner -> scanner.existsIf(Character::isDigit), IStringScanner::nextFloat))
						.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		alphaDigitAlphaPath.path(functional(scanner -> scanner.exists(">"), IStringScanner::nextChar))
				.end("Terminator", functional(scanner -> scanner.exists(";"), IStringScanner::nextChar));

		alphaDigitAlphaPath.end("LeftCap", functional(scanner -> scanner.exists("("), IStringScanner::nextChar));

		SearchTree<String, IStringScanner> tree = builder.build();
		assertThat(tree.search(new StringScanner("alpha123alpha(")), is("LeftCap"));
		assertThat(tree.search(new StringScanner("alpha123alpha>;")), is("Terminator"));
	}

	@Test
	public void builder() {
		SearchTree.Builder<String, IStringScanner> builder = new SearchTree.Builder<>(
				functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		builder.path(functional(scanner -> scanner.existsIf(Character::isDigit), IStringScanner::nextFloat))
				.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha))
				.end("LeftCap", functional(scanner -> scanner.exists("("), IStringScanner::nextChar));

		builder.end("Terminator", functional(scanner -> scanner.exists(";"), IStringScanner::nextChar));

		SearchTree<String, IStringScanner> tree = builder.build();

		assertThat(tree.search(new StringScanner("alpha123alpha(")), is("LeftCap"));
		assertThat(tree.search(new StringScanner("alpha;")), is("Terminator"));
	}

	@Test
	public void simple_test() {
		SearchTree.Builder<String, IStringScanner> builder =
				new SearchTree.Builder<>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));
		builder.end("Digit",
				functional(scanner -> scanner.existsIf(Character::isDigit), IStringScanner::nextFloat));
		builder.end("LeftCap",
				functional(scanner -> scanner.exists("("), IStringScanner::nextChar));
		SearchTree<String, IStringScanner> tree = builder.build();

		assertThat(tree.search(new StringScanner("hello123")), is("Digit"));
		assertThat(tree.search(new StringScanner("print(")), is("LeftCap"));
	}
}
