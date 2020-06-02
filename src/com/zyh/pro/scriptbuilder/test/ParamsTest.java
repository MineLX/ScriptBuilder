package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.Params;
import com.zyh.pro.scriptbuilder.main.value.Value;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParamsTest {
	@Test
	public void simple_test() {
		Params params = new Params();
		params.add(new Value("value1"));
		assertThat(params.get(0).asString(), is("value1"));
	}
}
