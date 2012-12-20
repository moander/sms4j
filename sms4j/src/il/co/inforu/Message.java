package il.co.inforu;

public class Message {
    public Message(User user, Content content, Recipients recipients, Settings settings) {
        this.user = user;
        this.content = content;
        this.recipients = recipients;
        this.settings = settings;
    }
    
    public String toXml() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<Inforu>");

        builder.append(user.toXml());
        builder.append(content.toXml());
        builder.append(recipients.toXml());
        builder.append(settings.toXml());
        
        builder.append("</Inforu>");
        return builder.toString();
    }
    
    private final User user;
    private final Content content;
    private final Recipients recipients;
    private final Settings settings;
}
