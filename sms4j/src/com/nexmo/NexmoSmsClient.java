package com.nexmo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.codehaus.jackson.map.ObjectMapper;

import com.clickatell.QueryStringBuilder;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.googlecode.sms4j.SmsClient;
import com.googlecode.sms4j.SmsException;

public class NexmoSmsClient implements SmsClient {
	private static final String NEXMO_GATEWAY_URL = "https://rest.nexmo.com/sms/json";
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private final HttpRequestFactory requestFactory;
	private final String apiKey;
	private final String apiSecret;
	private final Integer connectTimeout;
	private final Integer readTimeout;
	
	public NexmoSmsClient(HttpRequestFactory requestFactory, String apiKey, String apiSecret, Integer connectTimeout, Integer readTimeout) {
		this.requestFactory = requestFactory;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	public NexmoSmsClient(HttpRequestFactory requestFactory, String apiKey, String apiSecret) {
		this(requestFactory, apiKey, apiSecret, null, null);
	}

	public String send(String srcName, String srcNumber, String destNumber, String text)
	throws IOException, SmsException {
		final Response response = sendImpl(((srcName != null) ? srcName : srcNumber), destNumber, text);
		if (response.messageCount() != 1) {
			throw new IOException("Got invalid messageCount: " + response.messageCount);
		}
		
		if (response.messages.size() != 1) {
			throw new IOException("Got invalid number of message responses: " + response.messages.size());
		}
		
		final MessageResponse messageResponse = response.messages.get(0);
		if (messageResponse.status() != 0) {
			throw new NexmoSmsException(messageResponse);
		}
		
		return messageResponse.messageId;
	}
	
	private Response sendImpl(String from, String destNumber, String text) throws IOException {
		final QueryStringBuilder query = new QueryStringBuilder();
		query.append("username", apiKey);
		query.append("password", apiSecret);
		query.append("from", from);
		query.append("to", destNumber);
		query.append("type", "text");
		query.append("text", text);
		
    	final HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(NEXMO_GATEWAY_URL + query.toString()));
        if (connectTimeout != null) {
        	request.setConnectTimeout(connectTimeout.intValue());
        }
        if (readTimeout != null) {
        	request.setReadTimeout(readTimeout);
        }
		
        final HttpResponse response = request.execute();
        try {
            final Reader reader = new InputStreamReader(response.getContent(), "UTF-8");
            try {
            	return mapper.readValue(reader, Response.class);
            } finally {
                reader.close();
            }
        } finally {
        	response.ignore();
        }
	}
}
