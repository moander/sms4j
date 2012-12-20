package il.co.inforu;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.googlecode.sms4j.SmsClient;
import com.googlecode.sms4j.SmsException;

public class InforuSmsClientTest {
	private static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    
    @Test
    public void testWrongCredentials() throws IOException, SmsException {
        final SmsClient client = new InforuSmsClient(requestFactory, "_test_username_", "_test_password_");
        try {
            client.send("test", "+972540000000", DEST_NUMBER, "hello from inforu! (testWrongCredentials)");
            fail("expected exception");
        } catch (SmsException e) {
            // expected exception
        }
    }
    
    @Test
    public void testSms() throws IOException, SmsException {
        final SmsClient client = new InforuSmsClient(requestFactory, INFORU_USERNAME, INFORU_PASSWORD);
        client.send("test", "+972540000000", DEST_NUMBER, "hello from inforu! (testSms)");
    }
    
    // TODO: fill these in with real values
	private static final String INFORU_USERNAME = "";
	private static final String INFORU_PASSWORD = "";
    private static final String DEST_NUMBER = "";
}
