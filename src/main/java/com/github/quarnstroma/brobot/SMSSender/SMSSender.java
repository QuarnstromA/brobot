package com.github.quarnstroma.brobot.SMSSender;

import com.twilio.sdk.TwilioRestClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SMSSender
{
    static SMSSender instance;
    Properties props;
    TwilioRestClient twilioClient;
    HttpClient httpClient;
    HttpPost post;

    public static SMSSender getInstance()
    {
        if(null == instance)
        {
            try
            {
                instance = new SMSSender();
            }catch (IOException e)
            {
                System.err.println("Failed to create SMSSender: " + e.getMessage());
                System.exit(1);
            }
        }
        return instance;
    }

    private SMSSender() throws IOException
    {
        loadProperties();
        twilioClient = initTwilioClient();
        httpClient = HttpClients.createDefault();
        post = new HttpPost("https://api.twilio.com/2010-04-01/Accounts/"+twilioClient.getAccountSid()+"/Messages.json");
    }

    private TwilioRestClient initTwilioClient()
    {
        return new TwilioRestClient(props.getProperty("ACCOUNT_SID"), props.getProperty("AUTH_TOKEN"));
    }

    private void loadProperties() throws IOException
    {
        props = new Properties();
        InputStream fIn = getClass().getResourceAsStream("/config.properties");
        props.load(fIn);
    }

    public boolean sendSMS(String message){
        List<NameValuePair> messageParams = createMessagePayload(message);
        try {
            getTwilioClient()
                    .getAccount()
                    .getMessageFactory()
                    .create(messageParams);
            getHttpClient().execute(post);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<NameValuePair> createMessagePayload(String message)
    {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("From", props.getProperty("FROM_NUMBER")));
        params.add(new BasicNameValuePair("To", props.getProperty("TO_NUMBER")));
        params.add(new BasicNameValuePair("Body", message));
        return params;
    }

    public TwilioRestClient getTwilioClient()
    {
        return twilioClient;
    }

    public void setTwilioClient(TwilioRestClient twilioClient)
    {
        this.twilioClient = twilioClient;
    }

    public HttpClient getHttpClient()
    {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient)
    {
        this.httpClient = httpClient;
    }
}
