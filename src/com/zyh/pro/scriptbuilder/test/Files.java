package com.zyh.pro.scriptbuilder.test;

import java.io.*;

public class Files {

	public static String toString(String path) throws FileNotFoundException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		InputStream input;
		if (path.contains(":"))
			input = new FileInputStream(path);
		else {
			input = Files.class.getResourceAsStream(path);
		}
		int len;
		byte[] buffer = new byte[1024];
		try {
			while ((len = input.read(buffer)) != -1)
				outputStream.write(buffer, 0, len);
			return new String(outputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
