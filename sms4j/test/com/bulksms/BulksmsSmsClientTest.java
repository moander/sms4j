package com.bulksms;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.bulksms.BulksmsSmsClient.RoutingGroup;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.openrest.sms4j.SmsException;

public class BulksmsSmsClientTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
	
    @Test
    public void testSendWithWrongCredentials() throws IOException, SmsException {
        final BulksmsSmsClient client = new BulksmsSmsClient(requestFactory, "_test_username_", "_test_password_", BULKSMS_ROUTING_GROUP);
        try {
            client.send("test", "+972540000000", DEST_NUMBER, "hello from bulksms! (testWrongCredentials)");
            fail("expected exception");
        } catch (SmsException e) {
            // expected exception
        }
    }
    
    @Test
    public void testSendSms() throws IOException, SmsException {
        final BulksmsSmsClient client = new BulksmsSmsClient(requestFactory, BULKSMS_USERNAME, BULKSMS_PASSWORD, BULKSMS_ROUTING_GROUP);
        final String id = client.send(null, "+972540000000", DEST_NUMBER, "hello from bulksms! (testSms)");
        System.out.println(id);
        assertNotNull(id);
    }
	
    @Test
    public void testStatusWithWrongCredentials() throws IOException, SmsException {
        final BulksmsSmsClient client = new BulksmsSmsClient(requestFactory, "_test_username_", "_test_password_", BULKSMS_ROUTING_GROUP);
        final Status status = client.status("123");
        assertEquals(23, status.getCode());
    }
    
    @Test
    public void testStatus() throws IOException, SmsException {
        final BulksmsSmsClient client = new BulksmsSmsClient(requestFactory, BULKSMS_USERNAME, BULKSMS_PASSWORD, BULKSMS_ROUTING_GROUP);
        final Status status = client.status("123");
        assertEquals(0, status.getCode());
    }
    
    // TODO: fill these in with real values
    private static final String BULKSMS_USERNAME = "";
    private static final String BULKSMS_PASSWORD = "";
    private static final RoutingGroup BULKSMS_ROUTING_GROUP = RoutingGroup.ECONOMY;
    private static final String DEST_NUMBER = "";
}
