package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.Scanner;
import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import static com.zyh.pro.scanner.main.Matcher.functional;
import static java.lang.String.valueOf;
import static java.lang.System.out;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SearchTreeTest {

	@Test
	public void printDealer() {
		ScriptContext context = new ScriptContext(out);

		SearchTree.Builder<OperationInterpreter, IScanner> builder =
				new SearchTree.Builder<>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha));

		builder.end(null, functional(scanner -> false, scanner -> {}));
		builder.end(new AssignInterpreter(context), functional(scanner -> false, scanner -> {}));

		SearchTree.Builder<OperationInterpreter, IScanner>.Path leftCapPath =
				builder.path(functional(scanner -> scanner.exists("("), IScanner::nextChar));
		leftCapPath.end(null, functional(scanner -> false, scanner -> {}));

		leftCapPath.path(functional(scanner -> scanner.existsIf(Character::isDigit), IScanner::nextFloat))
				.path(functional(scanner -> scanner.exists(")"), IScanner::nextChar))
				.end(new InvokeFunctionInterpreter(context), functional(scanner -> scanner.exists(";"), IScanner::nextChar));

		SearchTree<OperationInterpreter, IScanner> build = builder.build();

		OperationInterpreter operationInterpreter = build.search(new Scanner("print(456);"));
		assertTrue(operationInterpreter instanceof InvokeFunctionInterpreter);
	}

	@Test
	public void tree() {
		ScriptContext context = new ScriptContext(out);

		SearchTree<OperationInterpreter, IScanner> tree = new SearchTree.Builder<OperationInterpreter, IScanner>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IScanner::nextAlpha))
				.path(functional(scanner -> scanner.exists("="), IScanner::nextChar))
				.path(functional(scanner -> scanner.existsIf(Character::isDigit), IScanner::nextFloat))
				.end(new AssignInterpreter(context), functional(scanner -> scanner.exists(";"), IScanner::nextChar))
				.build();

		OperationInterpreter operationInterpreter = tree.search(new Scanner("a=6;"));
		assertTrue(operationInterpreter instanceof AssignInterpreter);
	}
}
