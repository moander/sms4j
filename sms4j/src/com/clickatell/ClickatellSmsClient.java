package com.clickatell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.googlecode.sms4j.SmsClient;
import com.googlecode.sms4j.SmsException;

public class ClickatellSmsClient implements SmsClient {
	private static final String CLICKATELL_GATEWAY_URL = "https://api.clickatell.com/http/";
	
	private final HttpRequestFactory requestFactory;
	private final String apiId;
	private final String username;
	private final String password;
	private final Integer connectTimeout;
	private final Integer readTimeout;
    
	public ClickatellSmsClient(HttpRequestFactory requestFactory, String apiId, String username, String password,
			Integer connectTimeout, Integer readTimeout) {
		this.requestFactory = requestFactory;
		this.apiId = apiId;
		this.username = username;
		this.password = password;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	public ClickatellSmsClient(HttpRequestFactory requestFactory, String apiId, String username, String password) {
		this(requestFactory, apiId, username, password, null, null);
	}
	
	public String send(String srcName, String srcNumber, String destNumber, String text)
	throws IOException, SmsException {
		final String result = sendImpl(((srcName != null) ? srcName : srcNumber), destNumber, text);
		
		if (result == null) {
			throw new SmsException("Invalid result");
		} else if (result.startsWith("ID: ")) {
			return result.substring(4);
		} else {
			throw new SmsException(result);
		}
	}

	private String sendImpl(String from, String destNumber, String text) throws IOException {
		final QueryStringBuilder query = new QueryStringBuilder();
		query.append("user", username);
		query.append("password", password);
		query.append("api_id", apiId);
		query.append("to", destNumber);
		if (from != null) {
			query.append("from", from);
		}
		query.append("text", text);
		
    	final HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(
    			CLICKATELL_GATEWAY_URL + "sendmsg" + query.toString()));
        if (connectTimeout != null) {
        	request.setConnectTimeout(connectTimeout.intValue());
        }
        if (readTimeout != null) {
        	request.setReadTimeout(readTimeout);
        }
		
        final HttpResponse response = request.execute();
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(response.getContent(), "UTF-8"));
            try {
            	return br.readLine();
            } finally {
                br.close();
            }
        } finally {
        	response.ignore();
        }
	}
}
