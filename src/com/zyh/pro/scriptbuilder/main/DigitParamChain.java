package com.zyh.pro.scriptbuilder.main;

import com.zyh.pro.scanner.main.ReturnChain;
import com.zyh.pro.scanner.main.Scanner;

public class DigitParamChain extends ReturnChain<String, String> {
	@Override
	protected String onConsume(String s) {
		return s;
	}

	@Override
	protected boolean isConsumable(String s) {
		return new Scanner(s).existsIf(Character::isDigit);
	}
}
