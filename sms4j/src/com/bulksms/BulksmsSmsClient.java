package com.bulksms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.openrest.sms4j.SmsClient;
import com.openrest.sms4j.SmsException;

public class BulksmsSmsClient implements SmsClient {
	private static final String BULKSMS_GATEWAY_URL = "https://bulksms.vsms.net/eapi/";

	private final HttpRequestFactory requestFactory;
	private final String username;
	private final String password;
	private final RoutingGroup routingGroup;
	private final Integer connectTimeout;
	private final Integer readTimeout;
	
	public static enum RoutingGroup {
		ECONOMY(1), STANDARD(2), PREMIUM(3);

		private final int id;
		private RoutingGroup(int id) {
			this.id = id;
		}
		public int getId() {
			return id;
		}
	}
    
	public BulksmsSmsClient(HttpRequestFactory requestFactory, String username, String password, RoutingGroup routingGroup) {
		this(requestFactory, username, password, routingGroup, null, null);
	}
	
	public BulksmsSmsClient(HttpRequestFactory requestFactory, String username, String password, RoutingGroup routingGroup,
			Integer connectTimeout, Integer readTimeout) {
		this.requestFactory = requestFactory;
		this.username = username;
		this.password = password;
		this.routingGroup = routingGroup;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	public String send(String srcName, String srcNumber, String destNumber, String text)
	throws IOException, SmsException {
		final String result = sendImpl(((srcName != null) ? srcName : removeLeadingPlus(srcNumber)),
				removeLeadingPlus(destNumber), text);
		
		final String[] parts = result.split("\\|");
		if (parts.length < 2) {
			throw new SmsException("Invalid result: " + result);
		}
		
		final int code = Integer.parseInt(parts[0]);
		final String description = parts[1];
		if (code != 0) {
			throw new BulksmsSmsException(new Status(code, description));
		}
			
		if (parts.length != 3) {
			throw new SmsException("Invalid result: " + result);
		}
		return parts[2];
	}
	
	/** @see http://www.bulksms.com/int/docs/eapi/submission/send_sms/ */
	private String sendImpl(String from, String destNumber, String text) throws IOException {
        final String url = BULKSMS_GATEWAY_URL + "submission/send_sms/2/2.0";
        
        final Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        params.put("message", text);
        params.put("msisdn", destNumber);
        params.put("sender", from);
        params.put("routing_group", Integer.toString(routingGroup.getId()));
        params.put("repliable", "0");
        
        return call(url, params);
	}
	
	public Status status(String msgId) throws IOException, SmsException {
		final String result = statusImpl(msgId);
		
		final String[] parts = result.split("\\|");
		if (parts.length < 2) {
			throw new SmsException("Invalid result: " + result);
		}
		
		final int code = Integer.parseInt(parts[0]);
		final String description = parts[1];
		
		return new Status(code, description);
	}
	
	/** @see http://www.bulksms.com/int/docs/eapi/status_reports/get_report/ */
	private String statusImpl(String msgId) throws IOException {
        final String url = BULKSMS_GATEWAY_URL + "status_reports/get_report/2/2.0";
        
        final Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        params.put("batch_id", msgId);
        
        return call(url, params);
	}
	
	private String call(String url, Map<String, String> params) throws IOException {
        // @see http://bulksms.vsms.net/docs/eapi/code_samples/java/
		final HttpContent content = new UrlEncodedContent(params);
    	final HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(url), content);
        if (connectTimeout != null) {
        	request.setConnectTimeout(connectTimeout.intValue());
        }
        if (readTimeout != null) {
        	request.setReadTimeout(readTimeout);
        }

        final HttpResponse response = request.execute();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getContent(), "UTF-8"));
            try {
            	final StringBuilder builder = new StringBuilder();
            	String line;
            	while ((line = reader.readLine()) != null) {
            		builder.append(line).append('\n');
            	}
            	
            	return builder.toString();
            } finally {
            	reader.close();
            }
        } finally {
        	response.ignore();
        }
	}
	
	private static String removeLeadingPlus(String phone) {
		return ((phone.charAt(0) != '+') ? phone : phone.substring(1)); 
	}
}
