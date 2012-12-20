package com.nexmo;

import com.googlecode.sms4j.SmsException;

public class NexmoSmsException extends SmsException {
    private static final long serialVersionUID = 1L;
    
    private final int status;
    private final String errorText;
    
    public NexmoSmsException(MessageResponse messageResponse) {
        super("status = " + messageResponse.status + ", errorText = " + messageResponse.errorText);
        this.status = messageResponse.status();
        this.errorText = messageResponse.errorText;
    }
    
    public int getStatus() {
    	return status;
    }
    
    public String getErrorText() {
    	return errorText;
    }
}
