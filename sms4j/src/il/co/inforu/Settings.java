package il.co.inforu;

public class Settings {
    public Settings(String senderNumber, String senderName) {
        this.senderNumber = senderNumber;
        this.senderName = senderName;
    }
    
    public Settings(String senderNumber) {
        this(senderNumber, null);
    }
    
    public String toXml() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<Settings>");
        
        Utils.appendIfNotNull(builder, "SenderNumber", senderNumber);
        Utils.appendIfNotNull(builder, "SenderName", senderName);
        
        builder.append("</Settings>");
        return builder.toString();
    }
    
    private final String senderNumber;
    private final String senderName;
}
