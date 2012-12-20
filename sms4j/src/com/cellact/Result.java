package com.cellact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Result {
    public Result(int code, String message, String blmj) {
        this.code = code;
        this.message = message;
        this.blmj = blmj;
    }
    
    public static Result fromXml(String xml) {
        final Matcher codeMatcher = codePattern.matcher(xml);
        if ((!codeMatcher.matches()) || (codeMatcher.groupCount() != 1)) {
            throw new IllegalArgumentException("Invalid Result XML: " + xml);
        }
        final int code = Integer.parseInt(codeMatcher.group(1));
        
        final Matcher messageMatcher = messagePattern.matcher(xml);
        if ((!messageMatcher.matches()) || (messageMatcher.groupCount() != 1)) {
            throw new IllegalArgumentException("Invalid Result XML: " + xml);
        }
        final String message = messageMatcher.group(1);
        
        final String blmj;
        final Matcher blmjMatcher = blmjPattern.matcher(xml);
        if ((blmjMatcher.matches()) && (blmjMatcher.groupCount() == 1)) {
            blmj = blmjMatcher.group(1);
        } else {
            blmj = null;
        }
        
        return new Result(code, message, blmj);
    }
    
    public final int code;
    public final String message;
    public final String blmj;
    
    private static final Pattern codePattern = Pattern.compile(".*<RESULTCODE>(.*)</RESULTCODE>.*");
    private static final Pattern messagePattern = Pattern.compile(".*<RESULTMESSAGE>(.*)</RESULTMESSAGE>.*");
    private static final Pattern blmjPattern = Pattern.compile(".*<BLMJ>(.*)</BLMJ>.*");
}
