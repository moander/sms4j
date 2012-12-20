package com.clickatell;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.openrest.sms4j.SmsClient;
import com.openrest.sms4j.SmsException;

public class ClickatellSmsClientTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    
    @Test
    public void testWrongCredentials() throws IOException, SmsException {
        final SmsClient client = new ClickatellSmsClient(requestFactory, "_test_company_", "_test_username_", "_test_password_");
        try {
            client.send("test", "+972540000000", DEST_NUMBER, "hello from clickatell! (testWrongCredentials)");
            fail("expected exception");
        } catch (SmsException e) {
            // expected exception
        }
    }
    
    @Test
    public void testSms() throws IOException, SmsException {
        final SmsClient client = new ClickatellSmsClient(requestFactory, CLICKATELL_API_ID, CLICKATELL_USERNAME, CLICKATELL_PASSWORD);
        final String id = client.send(CLICKATELL_SENDER_ID, "+972540000000", DEST_NUMBER, "hello from clickatell! (testSms)");
        assertNotNull(id);
    }
    
    // TODO: fill these in with real values
    private static final String CLICKATELL_API_ID = "";
    private static final String CLICKATELL_USERNAME = "";
    private static final String CLICKATELL_PASSWORD = "";
    private static final String CLICKATELL_SENDER_ID = "";
    private static final String DEST_NUMBER = "";
}
