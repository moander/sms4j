package il.co.inforu;

public class Recipients {
    public Recipients(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String toXml() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<Recipients>");
        
        Utils.appendIfNotNull(builder, "PhoneNumber", phoneNumber);
        
        builder.append("</Recipients>");
        return builder.toString();
    }
    
    private String phoneNumber;
}
