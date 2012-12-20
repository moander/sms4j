package com.cellact;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.openrest.sms4j.SmsClient;
import com.openrest.sms4j.SmsException;

public class CellactSmsClientTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    
    @Test
    public void testWrongCredentials() throws IOException, SmsException {
        final SmsClient client = new CellactSmsClient(requestFactory, "_test_company_", "_test_username_", "_test_password_");
        try {
            client.send("test", "+972540000000", DEST_NUMBER, "hello from cellact! (testWrongCredentials)");
            fail("expected exception");
        } catch (SmsException e) {
            // expected exception
        }
    }
    
    @Test
    public void testSms() throws IOException, SmsException {
        final SmsClient client = new CellactSmsClient(requestFactory, CELLACT_COMPANY, CELLACT_USERNAME, CELLACT_PASSWORD);
        client.send("test", "+972540000000", DEST_NUMBER, "hello from cellact! (testSms)");
    }
    
    // TODO: fill these in with real values
    private static final String CELLACT_COMPANY = "";
    private static final String CELLACT_USERNAME = "";
    private static final String CELLACT_PASSWORD = "";
    private static final String DEST_NUMBER = "";
}
