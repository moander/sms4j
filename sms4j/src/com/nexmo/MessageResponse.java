package com.nexmo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Default constructor for JSON deserialization. */
    public MessageResponse() {}
    
    public String status;
    
    public int status() {
    	return Integer.parseInt(status);
    }
    
    @JsonProperty("error-text")
    public String errorText;

    @JsonProperty("message-id")
    public String messageId;
    
    @JsonProperty("client-ref")
    public String clientRef;
    
    @JsonProperty("remaining-balance")
    public String remainingBalance;
    
    public double remainingBalance() {
    	return Double.parseDouble(remainingBalance);
    }
    
    @JsonProperty("message-price")
    public String messagePrice;
    
    public double messagePrice() {
    	return Double.parseDouble(messagePrice);
    }
}
