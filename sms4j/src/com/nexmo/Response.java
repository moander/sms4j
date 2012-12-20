package com.nexmo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
