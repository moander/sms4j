package com.googlecode.sms4j;

import java.io.IOException;

/**
 * Client for bulk SMS services.
 * @author DL
 *
 */
public interface SmsClient {
    /**
     * Sends a single SMS message.
     * @param srcName        Source sender-ID.
     * @param srcNumber      Source phone number, e.g. "+972541234567".
     * @param destNumber     Destination phone number, e.g. "+972541234567".
     * @param text           Text to send.
     * @return service-specific unique message identifier, or empty string if no such id exists.
     * @throws IOException on communication errors with the SMS gateway.
     * @throws SmsException if the SMS could not be delivered.
     */
    public String send(String srcName, String srcNumber, String destNumber, String text) throws IOException, SmsException;
}
