package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import static com.zyh.pro.scanner.main.Matcher.functional;
import static java.lang.System.out;
import static org.junit.Assert.assertTrue;

public class SearchTreeTest {

	@Test
	public void printDealer() {
		ScriptContext context = new ScriptContext(out);

		SearchTree.Builder<OperationInterpreter, IStringScanner> builder =
				new SearchTree.Builder<>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		builder.end(null, functional(scanner -> false, scanner -> {}));
		builder.end(new AssignInterpreter(context), functional(scanner -> false, scanner -> {}));

		SearchTree.Builder<OperationInterpreter, IStringScanner>.Path leftCapPath =
				builder.path(functional(scanner -> scanner.exists("("), IStringScanner::nextChar));
		leftCapPath.end(null, functional(scanner -> false, scanner -> {}));

		leftCapPath.path(functional(scanner -> scanner.existsIf(Character::isDigit), IStringScanner::nextFloat))
				.path(functional(scanner -> scanner.exists(")"), IStringScanner::nextChar))
				.end(new InvokeFunctionInterpreter(context), functional(scanner -> scanner.exists(";"), IStringScanner::nextChar));

		SearchTree<OperationInterpreter, IStringScanner> build = builder.build();

		OperationInterpreter operationInterpreter = build.search(new StringScanner("print(456);"));
		assertTrue(operationInterpreter instanceof InvokeFunctionInterpreter);
	}

	@Test
	public void tree() {
		ScriptContext context = new ScriptContext(out);

		SearchTree<OperationInterpreter, IStringScanner> tree = new SearchTree.Builder<OperationInterpreter, IStringScanner>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha))
				.path(functional(scanner -> scanner.exists("="), IStringScanner::nextChar))
				.path(functional(scanner -> scanner.existsIf(Character::isDigit), IStringScanner::nextFloat))
				.end(new AssignInterpreter(context), functional(scanner -> scanner.exists(";"), IStringScanner::nextChar))
				.build();

		OperationInterpreter operationInterpreter = tree.search(new StringScanner("a=6;"));
		assertTrue(operationInterpreter instanceof AssignInterpreter);
	}
}
