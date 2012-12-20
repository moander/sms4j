package il.co.inforu;

public class Content {
    public Content(String message, String messagePelephone, String messageCellcom,
            String messageOrange, String messageMirs) {
        this.message = message;
        this.messagePelephone = messagePelephone;
        this.messageCellcom = messageCellcom;
        this.messageOrange = messageOrange;
        this.messageMirs = messageMirs;
    }
    
    public Content(String message) {
        this(message, null, null, null, null);
    }
    
    public String toXml() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<Content Type=\"sms\">");
        
        Utils.appendIfNotNull(builder, "Message", message);
        Utils.appendIfNotNull(builder, "MessagePelephone", messagePelephone);
        Utils.appendIfNotNull(builder, "MessageCellcom", messageCellcom);
        Utils.appendIfNotNull(builder, "MessageOrange", messageOrange);
        Utils.appendIfNotNull(builder, "MessageMirs", messageMirs);
        
        builder.append("</Content>");
        return builder.toString();
    }
    
    private final String message;
    private final String messagePelephone;
    private final String messageCellcom;
    private final String messageOrange;
    private final String messageMirs;
}
