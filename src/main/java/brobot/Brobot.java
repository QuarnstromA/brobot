package brobot;

import brobot.reminders.creatineReminder;
import com.twilio.sdk.TwilioRestClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import brobot.reminders.gymHoursReminder;

import java.io.InputStream;
import java.util.*;


/**
 * Created by eisenhorn on 8/28/2016.
 */
public class Brobot {

    static final long TWENTY_FOUR_HOURS = 86400000;

    TwilioRestClient twilioClient;
    HttpClient http;
    HttpPost post;
    Timer timer;
    Properties props;
    Boolean running;

    public Brobot() {
        try {
            props = new Properties();
            InputStream fIn = getClass().getResourceAsStream("/config.properties");
            props.load(fIn);
            System.setProperty("phantomjs.binary.path", props.getProperty("WEBDRIVER_PATH"));
            twilioClient = new TwilioRestClient(props.getProperty("ACCOUNT_SID"), props.getProperty("AUTH_TOKEN"));
            http = HttpClients.createDefault();
            post = new HttpPost("https://api.twilio.com/2010-04-01/Accounts/"+twilioClient.getAccountSid()+"/Messages.json");
            timer = new Timer();
            running = true;
        } catch (Exception e) {
            System.err.println("Exception " + e);
            running = false;
        }
    }

    public void run(){
        //Set up calendar to be tomorrow at 9am.
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1);
        timer.schedule(new gymHoursReminder(this), cal.getTime(), TWENTY_FOUR_HOURS);
        cal.set(Calendar.HOUR_OF_DAY, 20);
        timer.schedule(new creatineReminder(this), cal.getTime(), TWENTY_FOUR_HOURS);
    }

    public boolean sendSMS(String message){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("From", props.getProperty("FROM_NUMBER")));
        params.add(new BasicNameValuePair("To", props.getProperty("TO_NUMBER")));
        params.add(new BasicNameValuePair("Body", message));
        try {
            //The SMS is `somehow` added to the http post to the endpoint. I don't question it.
            twilioClient.getAccount().getMessageFactory().create(params);
            http.execute(post);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isRunning() {
        return running;
    }
}


