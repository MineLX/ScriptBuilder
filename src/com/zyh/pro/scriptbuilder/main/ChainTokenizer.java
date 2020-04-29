package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.IScanner;
import com.zyh.pro.scanner.main.ToResult;
import com.zyh.pro.scanner.main.TrimmedScanner;
import com.zyh.pro.scanner.test.Tokenizer;

public abstract class ChainTokenizer extends Tokenizer {

	private final ToResult<String, IScanner> nextGetter;

	protected ChainTokenizer(IScanner scanner) {
		super(new TrimmedScanner(scanner));
		nextGetter = create();
	}

	protected abstract ToResult<String, IScanner> create();

	@Override
	public final String next() {
		return nextGetter.get(scanner);
	}
}
