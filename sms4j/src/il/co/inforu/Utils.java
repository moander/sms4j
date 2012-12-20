package il.co.inforu;

import org.apache.commons.lang3.StringEscapeUtils;

public class Utils {
    private Utils() {}
    
    public static void appendIfNotNull(StringBuilder builder, String name, String value) {
        if (value != null) {
            builder.append('<');
            builder.append(name);
            builder.append('>');
            builder.append(StringEscapeUtils.escapeXml(value));
            builder.append("</");
            builder.append(name);
            builder.append('>');
        }
    }
}
