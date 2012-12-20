package com.clickatell;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QueryStringBuilder {
	public QueryStringBuilder() {}
	
	public void append(String name, String value) {
		builder.append(first ? '?' : '&')
			.append(urlEncode(name))
			.append('=')
			.append(urlEncode(value));
		first = false;
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}
	
	private static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean first = true;
	private final StringBuilder builder = new StringBuilder();
}
