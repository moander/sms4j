package il.co.inforu;

public enum Status {
    OK(1), Failed(-1), BadUserNameOrPassword(-2),
    UserNameNotExists(-3), PasswordNotExists(-4),
    RecipientsDataNotExists(-6), MessageTextNotExists(-9),
    IllegalXML(-11), UserQuotaExceeded(-13), ProjectQuotaExceeded(-14),
    CustomerQuotaExceeded(-15), WrongDateTime(-16), WrongRecipients(-18),
    InvalidSenderNumber(-20), InvalidSenderName(-21), UserBlocked(-22),
    UserAuthenticationError(-26), NetworkTypeNotSupported(-28),
    NotAllNetworkTypesSupported(-29);
    
    public static Status fromCode(int code) {
        for (Status status : Status.values()) {
            if (status.code == code) {
                return status;
            }
        }
        
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
    
    private Status(int code) {
        this.code = code;
    }
    
    public final int code;
}
