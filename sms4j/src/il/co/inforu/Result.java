package il.co.inforu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Result {
    public Result(Status status, String description, int numberOfRecipients) {
        this.status = status;
        this.description = description;
        this.numberOfRecipients = numberOfRecipients;
    }
    
    public static Result fromXml(String xml) {
        final Matcher matcher = pattern.matcher(xml);
        if ((!matcher.matches()) || (matcher.groupCount() != 3)) {
            throw new IllegalArgumentException("Invalid Result XML: " + xml);
        }
        
        final int code = Integer.parseInt(matcher.group(1));
        final String description = matcher.group(2);
        final int numberOfRecipients = Integer.parseInt(matcher.group(3));
        
        return new Result(Status.fromCode(code), description, numberOfRecipients);
    }
    
    public final Status status;
    public final String description;
    public final int numberOfRecipients;
    
    private static final Pattern pattern = Pattern.compile(
        "<Result><Status>(.*)</Status><Description>(.*)</Description><NumberOfRecipients>(.*)</NumberOfRecipients></Result>");
}
