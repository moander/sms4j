package com.cellact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.openrest.sms4j.SmsClient;
import com.openrest.sms4j.SmsException;

public class CellactSmsClient implements SmsClient {
    private static final String CELLACT_GATEWAY_URL =
            "https://cellactpro.net/GlobalSms/ExternalClient/GlobalAPI.asp";
    
    private final HttpRequestFactory requestFactory;
    private final String company;
    private final String username;
    private final String password;
    private final Integer connectTimeout;
    private final Integer readTimeout;
    
    public CellactSmsClient(HttpRequestFactory requestFactory, String company, String username, String password,
    		Integer connectTimeout, Integer readTimeout) {
    	this.requestFactory = requestFactory;
    	this.company = company;
        this.username = username;
        this.password = password;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }
    
    public CellactSmsClient(HttpRequestFactory requestFactory, String company, String username, String password) {
    	this(requestFactory, company, username, password, null, null);
    }
    
    public String send(String srcName, String srcNumber, String destNumber, String text) throws IOException, SmsException {
        final Result result = sendImpl(text, Collections.singletonList(destNumber),
                ((srcName != null) ? srcName : srcNumber));
        if (result.code != 0) {
            throw new CellactSmsException(result);
        }
        
        return result.blmj;
    }
    
    private Result sendImpl(String text, List<String> destinations, String source) throws IOException {
        final StringBuilder builder = new StringBuilder();
        
        builder.append("<PALO>");
        
        builder.append("<HEAD>");
        addTag(builder, "FROM", company);
        builder.append("<APP USER=\"").append(username).append("\" PASSWORD=\"").append(password).append("\"/>");
        addTag(builder, "CMD", "sendtextmt");
        builder.append("</HEAD>");
        
        builder.append("<BODY>");
        addTag(builder, "CONTENT", text);
        builder.append("<DEST_LIST>");
        for (String destination : destinations) {
            addTag(builder, "TO", destination);
        }
        builder.append("</DEST_LIST>");
        builder.append("</BODY>");
        
        builder.append("<OPTIONAL>");
        addTag(builder, "CALLBACK", source);
        builder.append("</OPTIONAL>");
        
        builder.append("</PALO>");

        final String resultXml = post(builder.toString());
        return Result.fromXml(resultXml);
    }
    
    private String post(String xmlString) throws IOException {
        final String encodedMessage = "XMLString=" + xmlString.replace(" ", "%20").replace("+", "%2B");
        final byte[] byteMessage = encodedMessage.getBytes("UTF-8");
		final HttpContent content = new ByteArrayContent("application/x-www-form-urlencoded", byteMessage);
		
    	final HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(CELLACT_GATEWAY_URL), content);
        if (connectTimeout != null) {
        	request.setConnectTimeout(connectTimeout.intValue());
        }
        if (readTimeout != null) {
        	request.setReadTimeout(readTimeout);
        }
        
        final HttpResponse response = request.execute();
        try {
            final StringBuilder builder = new StringBuilder();
            final BufferedReader br = new BufferedReader(new InputStreamReader(response.getContent(), "UTF-8"));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
            } finally {
                br.close();
            }
            return builder.toString();
        } finally {
        	response.ignore();
        }
    }
    
    private static void addTag(StringBuilder builder, String name, String content) {
        builder.append('<').append(name).append('>');
        builder.append(content);
        builder.append("</").append(name).append('>');
    }
}
