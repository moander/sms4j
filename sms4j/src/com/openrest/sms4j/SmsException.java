package com.openrest.sms4j;

public class SmsException extends Exception {
    public SmsException() {}
    
    public SmsException(String message) {
        super(message);
    }
    
    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SmsException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 1L;
}
