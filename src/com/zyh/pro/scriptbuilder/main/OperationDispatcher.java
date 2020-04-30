package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IStringScanner;
import com.zyh.pro.scanner.main.StringScanner;
import com.zyh.pro.scanner.main.TrimmedStringScanner;

import java.util.Optional;

import static com.zyh.pro.scanner.main.Matcher.functional;

public class OperationDispatcher {

	private final SearchTree<OperationInterpreter, IStringScanner> dealerSearchTree;

	public OperationDispatcher(ScriptContext context) {
		SearchTree.Builder<OperationInterpreter, IStringScanner> builder = new SearchTree.Builder<>(functional(scanner -> scanner.existsIf(Character::isAlphabetic), IStringScanner::nextAlpha));

		builder.path(functional(scanner -> scanner.exists("("), scanner -> scanner.between('(', ')')))
				.end(new InvokeFunctionInterpreter(context), functional(scanner -> scanner.exists(";"), IStringScanner::nextChar));

		dealerSearchTree = builder.path(functional(scanner -> scanner.exists("="), IStringScanner::nextChar))
				.path(functional(scanner -> true, scanner -> scanner.til(';')))
				.end(new AssignInterpreter(context), functional(scanner -> scanner.exists(";"), IStringScanner::nextChar)).build();
	}

	public OperationInterpreter getDealer(String command) {
		return dealerSearchTree.search(new TrimmedStringScanner(new StringScanner(command)));
	}

	public IOperation getOperation(String operationAsCommand) {
		return Optional.ofNullable(getDealer(operationAsCommand))
				.orElse(OperationInterpreter.empty())
				.interpret(operationAsCommand);
	}
}
