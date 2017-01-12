package com.github.quarnstroma.brobot.reminders;

import com.github.quarnstroma.brobot.SMSSender.SMSSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by eisenhorn on 8/29/2016.
 */
public class gymHoursReminder extends TimerTask{
    SMSSender sms;

    public gymHoursReminder()
    {
        sms = SMSSender.getInstance();
    }

    public void run() {
        Document doc = null;
        String message = null;
        try {
            doc = Jsoup.connect("https://www.facebook.com/BoiseStateRecreation").get();
            System.out.println(doc);
            Element state = doc.getElementsByClass("_1xm9").first();
            if(null==state){
                state = doc.getElementsByClass("_1xma").first();
            }
            System.out.println(state.text());
            Element time = doc.select("span._c24._50f3").first();
            System.out.println(time.text());
            message = "The gym is " + state.text() + " " + time.text();
        } catch (IOException e) {
            message = "I failed to get the time. Please check my logs.";
            System.out.println(e);
        } catch (NullPointerException e){
            message = "I'm broken.";
        }

        sms.sendSMS(message);
    }
}
