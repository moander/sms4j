package il.co.inforu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.googlecode.sms4j.SmsClient;
import com.googlecode.sms4j.SmsException;

public class InforuSmsClient implements SmsClient {
    private static final String INFORU_GATEWAY_URL = "https://api.inforu.co.il/SendMessageXml.ashx";
    
	private final HttpRequestFactory requestFactory;
    private final User user;
    private final Integer connectTimeout;
    private final Integer readTimeout;
	
    public InforuSmsClient(HttpRequestFactory requestFactory, String username, String password, Integer connectTimeout, Integer readTimeout) {
    	this.requestFactory = requestFactory;
        this.user = new User(username, password);
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }
    
    public InforuSmsClient(HttpRequestFactory requestFactory, String username, String password) {
    	this(requestFactory, username, password, null, null);
    }
    
    public String send(String srcName, String srcNumber, String destNumber, String text) throws IOException, SmsException {
        if ((srcNumber != null) && (srcNumber.charAt(0) == '+')) {
            srcNumber = srcNumber.substring(1);
        }
        
        final Result result = sendImpl(new Message(user, new Content(text), new Recipients(destNumber),
                new Settings(srcNumber, srcName)));
        
        if ((result.status != Status.OK) || (result.numberOfRecipients != 1)) {
            throw new InforuSmsException(result);
        }
        
        return "";
    }
    
    private Result sendImpl(Message inforuMessage) throws IOException {
        final String encodedMessage = "InforuXML=" + inforuMessage.toXml().replace(' ', '+');
        final byte[] byteMessage = encodedMessage.getBytes("UTF-8");
		final HttpContent content = new ByteArrayContent("application/x-www-form-urlencoded", byteMessage);
        
    	final HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(INFORU_GATEWAY_URL), content);
        if (connectTimeout != null) {
        	request.setConnectTimeout(connectTimeout.intValue());
        }
        if (readTimeout != null) {
        	request.setReadTimeout(readTimeout);
        }
        
        final HttpResponse response = request.execute();
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(response.getContent(), "UTF-8"));
            try {
                return Result.fromXml(br.readLine());
            } finally {
                br.close();
            }
        } finally {
        	response.ignore();
        }
    }
}
