package com.github.quarnstroma.brobot.SMSSender;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

public class SMSSenderTest
{
    @Test
    public void testGetInstance()
    {
        assertNotNull(SMSSender.getInstance());
    }

    @Test
    public void testCreateMessagePayload()
    {
        List<NameValuePair> listOfParams = SMSSender.getInstance().createMessagePayload("testMessage");
        assertNotNull(listOfParams);
    }

    @Test
    public void testSendMessage() throws TwilioRestException, IOException
    {
        SMSSender sender = SMSSender.getInstance();
        sender.setTwilioClient(mockTwilioRestClient());
        HttpClient mockHttp = mock(HttpClient.class);
        when(mockHttp.execute(any(HttpPost.class)))
                .thenReturn(null);
        sender.setHttpClient(mockHttp);
        if(!sender.sendSMS("testMessage"))
        {
            fail();
        }
    }

    private TwilioRestClient mockTwilioRestClient() throws TwilioRestException
    {
        MessageFactory mockMessFactory = mock(MessageFactory.class);
        when(mockMessFactory.create(any(List.class)))
                .thenReturn(mock(Message.class));
        Account mockAccount = mock(Account.class);
        when(mockAccount.getMessageFactory())
                .thenReturn(mockMessFactory);
        TwilioRestClient mockTwilio = mock(TwilioRestClient.class);
        when(mockTwilio.getAccount()
        ).thenReturn(mockAccount);
        return mockTwilio;
    }
}
