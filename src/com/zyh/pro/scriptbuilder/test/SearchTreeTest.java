package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scanner.main.*;
import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.operation.IOperation;
import com.zyh.pro.scriptbuilder.main.parser.AssignParser;
import com.zyh.pro.scriptbuilder.main.parser.FunctionInvocationParser;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.zyh.pro.scanner.main.Matcher.functional;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SearchTreeTest {
	@Test
	public void print_assign() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ScriptContext context = new ScriptContext(new PrintStream(output));
		context.addFunction(new PrintFunction(context));

		SearchTree.Builder<ToResult<IOperation, IStringScanner>, IStringScanner> builder = new SearchTree.Builder<>();
		SearchTree.Builder<ToResult<IOperation, IStringScanner>, IStringScanner>.Path alphaPath = builder.path(functional(
				scanner -> scanner.existsIf(Character::isAlphabetic),
				IStringScanner::nextPage));

		alphaPath.end(new AssignParser(context)::onMatched, functional(scanner -> scanner.exists("="), IStringScanner::nextChar));
		alphaPath.end(new FunctionInvocationParser(context)::onMatched, functional(scanner -> scanner.exists("("), IStringScanner::nextChar));

		ToResult<ToResult<IOperation, IStringScanner>, IStringScanner> build = builder.build();

		StringScanner assignExpr = new StringScanner("a=6;");
		build.get(assignExpr.copy()).get(assignExpr).execute();
		assertThat(context.getVariable("a").asString(), is("6"));

		StringScanner printExpr = new StringScanner("print(a);");
		build.get(printExpr.copy()).get(printExpr).execute();
		assertThat(new String(output.toByteArray()), is("6"));
	}

	@Test
	public void double_path() {
		SearchTree.Builder<String, IStringScanner> builder = new SearchTree.Builder<>();

		SearchTree.Builder<String, IStringScanner>.Path alphaPath =
				builder.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		SearchTree.Builder<String, IStringScanner>.Path alphaDigitAlphaPath =
				alphaPath.path(functional(scanner -> scanner.existsIf(Character::isDigit), IStringScanner::nextFloat))
						.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		alphaDigitAlphaPath.path(functional(scanner -> scanner.exists(">"), IStringScanner::nextChar))
				.end("Terminator", functional(scanner -> scanner.exists(";"), IStringScanner::nextChar));

		alphaDigitAlphaPath.end("LeftCap", functional(scanner -> scanner.exists("("), IStringScanner::nextChar));

		ToResult<String, IStringScanner> tree = builder.build();
		assertThat(tree.get(new StringScanner("alpha123alpha(")), is("LeftCap"));
		assertThat(tree.get(new StringScanner("alpha123alpha>;")), is("Terminator"));
	}

	@Test
	public void builder() {
		SearchTree.Builder<String, IStringScanner> builder = new SearchTree.Builder<>();

		SearchTree.Builder<String, IStringScanner>.Path alphaPath =
				builder.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		alphaPath.path(functional(scanner -> scanner.existsIf(Character::isDigit), IStringScanner::nextFloat))
				.path(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha))
				.end("LeftCap", functional(scanner -> scanner.exists("("), IStringScanner::nextChar));

		alphaPath.end("Terminator", functional(scanner -> scanner.exists(";"), IStringScanner::nextChar));

		ToResult<String, IStringScanner> tree = builder.build();

		assertThat(tree.get(new StringScanner("alpha123alpha(")), is("LeftCap"));
		assertThat(tree.get(new StringScanner("alpha;")), is("Terminator"));
	}
}
