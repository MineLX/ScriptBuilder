package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.ReturnChain;
import com.zyh.pro.scanner.main.Scanner;

public class StringParamChain extends ReturnChain<String, String> {
	@Override
	protected String onConsume(String paramsAsText) {
		Scanner scanner = new Scanner(paramsAsText);
		scanner.nextChar();
		return scanner.til('"');
	}

	@Override
	protected boolean isConsumable(String paramsAsText) {
		return paramsAsText.startsWith("\"");
	}
}
