package il.co.inforu;

import com.openrest.sms4j.SmsException;

public class InforuSmsException extends SmsException {
    public InforuSmsException(Result result) {
        super("description = " + result.description + ", status = " + result.status + ", recipients = " + result.numberOfRecipients);
        this.result = result;
    }
    
    public final Result result;
    
    private static final long serialVersionUID = 1L;
}
