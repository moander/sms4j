package il.co.inforu;

public class User {
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String toXml() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<User>");
        
        Utils.appendIfNotNull(builder, "Username", username);
        Utils.appendIfNotNull(builder, "Password", password);
        
        builder.append("</User>");
        return builder.toString();
    }
    
    private final String username;
    private final String password;
}
