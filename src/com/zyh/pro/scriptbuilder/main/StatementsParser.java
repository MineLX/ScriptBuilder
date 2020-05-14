package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.ToResult;

import java.util.Scanner;

import static com.zyh.pro.scanner.main.Matcher.functional;

public class StatementsParser implements ToResult<IOperation, IStringScanner> {

	private final ToResult<ToResult<IOperation, IStringScanner>, IStringScanner> parserTree;

	public StatementsParser(ScriptContext context) {
		SearchTree.Builder<ToResult<IOperation, IStringScanner>, IStringScanner> builder = new SearchTree.Builder<>();
		builder.end(new ReturnParser(context),
				functional(scanner -> scanner.exists("return"), scanner -> scanner.pass("return")));
		SearchTree.Builder<ToResult<IOperation, IStringScanner>, IStringScanner>.Path alphaPath = builder.path(functional(
				scanner -> scanner.existsIf(Character::isAlphabetic),
				IStringScanner::nextPage));
		alphaPath.end(new AssignParser(context)::onMatched, functional(scanner -> scanner.exists("="), IStringScanner::nextChar));
		alphaPath.end(new FunctionInvocationParser(context)::onMatched, functional(scanner -> scanner.exists("("), IStringScanner::nextChar));
		parserTree = builder.build();
	}

	@Override
	public IOperation get(IStringScanner scanner) {
		CompositeOperation result = new CompositeOperation();
		while (scanner.hasNext()) {
			ToResult<IOperation, IStringScanner> parser = parserTree.get(scanner.copy());
			if (parser == null) break;
			result.add(parser.get(scanner));
		}
		return result;
	}
}
