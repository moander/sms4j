package com.bulksms;

import com.googlecode.sms4j.SmsException;

public class BulksmsSmsException extends SmsException {
    private static final long serialVersionUID = 1L;
    
    private final Status status;
    
    public BulksmsSmsException(Status status) {
        super("code = " + status.getCode() + ", description = " + status.getDescription());
        this.status = status;
    }
    
    public Status getStatus() {
    	return status;
    }
}
