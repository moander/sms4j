package com.vianett;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicLong;

import com.clickatell.QueryStringBuilder;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.openrest.sms4j.SmsClient;
import com.openrest.sms4j.SmsException;

public class ViaNettSmsClient implements SmsClient {
	private static final String VIANETT_GATEWAY_URL = "https://smsc.vianett.no/v3/send.ashx";
	
	private final HttpRequestFactory requestFactory;
	private final String username;
	private final String password;
	private final Integer connectTimeout;
	private final Integer readTimeout;
    
	private static AtomicLong refno = new AtomicLong(System.currentTimeMillis() / 1000L);
	
	public ViaNettSmsClient(HttpRequestFactory requestFactory, String username, String password,
			Integer connectTimeout, Integer readTimeout) {
		this.requestFactory = requestFactory;
		this.username = username;
		this.password = password;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	public ViaNettSmsClient(HttpRequestFactory requestFactory, String username, String password) {
		this(requestFactory, username, password, null, null);
	}
	
	public String send(String srcName, String srcNumber, String destNumber, String text) throws IOException, SmsException {
		final String result = sendImpl(((srcName != null) ? srcName : srcNumber), destNumber, text);
		
		if (result == null) {
			throw new SmsException("Invalid result");
		} else if (result.startsWith("200|OK")) {
			return result.substring(7);
		} else {
			throw new SmsException(result);
		}
	}

	private String sendImpl(String from, String destNumber, String text) throws IOException {
		final QueryStringBuilder query = new QueryStringBuilder();
		String srefno = Long.toString(refno.incrementAndGet());
		query.append("refno", srefno);
		query.append("user", username);
		query.append("pass", password);
		query.append("dst", destNumber);
		if (from != null) {
			query.append("src", from);
		}
		query.append("msg", text);
		
    	final HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(VIANETT_GATEWAY_URL + query.toString()));

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
            	return br.readLine() + "|" + srefno;
            } finally {
                br.close();
            }
        } finally {
        	response.ignore();
        }
	}
}
