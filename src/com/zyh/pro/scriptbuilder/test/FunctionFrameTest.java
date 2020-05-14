package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import org.junit.Test;

import static java.lang.System.out;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FunctionFrameTest {
	@Test
	public void returnValue() {
		FunctionFrame frame = new FunctionFrame(singletonList("paramName"), singletonList(new Value("paramValue")));
		frame.setReturnValue(new Value("returnValue"));
		assertThat(frame.getReturnValue().asString(), is("returnValue"));
	}

	@Test
	public void paramValue() {
		FunctionFrame frame = new FunctionFrame(singletonList("paramName"), singletonList(new Value("paramValue")));
		assertThat(frame.getParamValue("paramName").asString(), is("paramValue"));
		frame.setParamValue("paramName", new Value("paramValue_Modified"));
		assertThat(frame.getParamValue("paramName").asString(), is("paramValue_Modified"));
		assertThat(frame.modifyParamValue("paramName", new Value("paramValue_Modified_again")), is(true));
		assertThat(frame.modifyParamValue("non-exists Param Name", new Value("can't be modified.")), is(false));
	}
}
