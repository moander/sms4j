package com.nexmo;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.googlecode.sms4j.SmsClient;
import com.googlecode.sms4j.SmsException;

public class NexmoSmsClientTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    
    @Test
    public void testWrongCredentials() throws IOException, SmsException {
        final SmsClient client = new NexmoSmsClient(requestFactory, "_test_key_", "_test_secret_");
        try {
            client.send("test", "+972540000000", DEST_NUMBER, "hello from nexmo! (testWrongCredentials)");
            fail("expected exception");
        } catch (SmsException e) {
            // expected exception
        }
    }
    
    @Test
    public void testSms() throws IOException, SmsException {
        final SmsClient client = new NexmoSmsClient(requestFactory, NEXMO_API_KEY, NEXMO_API_SECRET);
        final String id = client.send(NEXMO_SENDER_ID, "+972540000000", DEST_NUMBER, "hello from nexmo! (testSms)");
        assertNotNull(id);
    }
    
    // TODO: fill these in with real values
    private static final String NEXMO_API_KEY = "";
    private static final String NEXMO_API_SECRET = "";
    private static final String NEXMO_SENDER_ID = "";
    private static final String DEST_NUMBER = "";
}
