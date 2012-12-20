package com.cellact;

import com.googlecode.sms4j.SmsException;

public class CellactSmsException extends SmsException {
    public CellactSmsException(Result result) {
        super("message = " + result.message + ", code = " + result.code + ", blmj = " + result.blmj);
        this.result = result;
    }
    
    public final Result result;
    
    private static final long serialVersionUID = 1L;
}
