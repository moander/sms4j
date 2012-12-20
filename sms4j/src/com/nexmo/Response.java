package com.nexmo;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Default constructor for JSON deserialization. */
    public Response() {}
    
    @JsonProperty("message-count")
    public String messageCount;
    
    public int messageCount() {
    	return Integer.parseInt(messageCount);
    }
    
    public List<MessageResponse> messages;
}
