package com.zyh.pro.scriptbuilder.test;

import com.zyh.pro.scriptbuilder.main.*;
import com.zyh.pro.scriptbuilder.main.value.Value;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class VarManagerTest {
	@Test
	public void edge_setVariable() {
		VarManager manager = new VarManager();
		manager.pushFrame(singletonList("paramName"), singletonList(new Value("paramValue")));
		manager.setVariable("non-exists", new Value("value"));
		assertThat(manager.global("non-exists").asString(), is("value"));
	}

	@Test
	public void frame() {
		VarManager manager = new VarManager();
		manager.pushFrame(singletonList("paramName"), singletonList(new Value("paramValue")));
		FunctionFrame frame = manager.popFrame();
		assertThat(frame.getParamValue("paramName").asString(), is("paramValue"));
	}

	@Test
	public void setVariable() {
		VarManager manager = new VarManager();
		manager.setVariable("param", new Value("global"));
		assertThat(manager.getVariable("param").asString(), is("global"));
		manager.pushFrame(singletonList("param"), singletonList(new Value("temporary")));
		assertThat(manager.getVariable("param").asString(), is("temporary"));
		manager.setVariable("param", new Value("temporaryModified"));
		assertThat(manager.getVariable("param").asString(), is("temporaryModified"));
		manager.popFrame();
		assertThat(manager.getVariable("param").asString(), is("global"));
	}

	@Test
	public void getVariable() {
		VarManager manager = new VarManager();
		manager.setGlobal("param", new Value("global"));
		assertThat(manager.getVariable("param").asString(), is("global"));
		manager.pushFrame(singletonList("param"), singletonList(new Value("temporary")));
		assertThat(manager.getVariable("param").asString(), is("temporary"));
		manager.popFrame();
		assertThat(manager.getVariable("param").asString(), is("global"));
	}

	@Test
	public void popTemporary() {
		VarManager manager = new VarManager();
		manager.pushFrame(singletonList("param"), singletonList(new Value("temporary")));
		assertThat(manager.temporary("param").asString(), is("temporary"));
		manager.popFrame();
		assertNull(manager.temporary("param"));
	}

	@Test
	public void getTemporary() {
		VarManager manager = new VarManager();
		manager.pushFrame(asList("param1", "param2"), asList(new Value("1"), new Value("2")));
		assertThat(manager.temporary("param1").asString(), is("1"));
	}

	@Test
	public void setGlobal() {
		VarManager manager = new VarManager();
		manager.setGlobal("a", new Value("1"));
		assertThat(manager.global("a").asString(), is("1"));
	}
}
