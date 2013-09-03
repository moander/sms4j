package com.vianett;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.openrest.sms4j.SmsClient;
import com.openrest.sms4j.SmsException;

public class ViaNettSmsClientTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    
    @Test
    public void testWrongCredentials() throws IOException, SmsException {
        final SmsClient client = new ViaNettSmsClient(requestFactory, "_sms4j_testWrongCredentials_", "_test_password_");
        try {
            client.send(null, "sms4j", DEST_NUMBER, "hello from ViaNett! (testWrongCredentials)");
            fail("expected exception");
        } catch (SmsException e) {
            // expected exception
        	assertTrue(e.getMessage().startsWith("100|Login failed"));
        }
    }
    
    @Test
    public void testSms() throws IOException, SmsException {
        final SmsClient client = new ViaNettSmsClient(requestFactory, VIANETT_USERNAME, VIANETT_PASSWORD);
        final String id = client.send(null, "sms4j", DEST_NUMBER, "hello from ViaNett! (testSms)");
        assertTrue(Long.parseLong(id) > 0);
    }
    
    private static final String VIANETT_USERNAME = "junitnocredits";
    private static final String VIANETT_PASSWORD = "test";
    private static final String DEST_NUMBER = "+972540000000";
}
