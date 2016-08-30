package brobot.reminders;

import brobot.Brobot;

import java.util.TimerTask;

/**
 * Created by eisenhorn on 8/29/2016.
 */
public class creatineReminder extends TimerTask {

    Brobot brobot;

    public creatineReminder(Brobot brobot){
        this.brobot=brobot;
    }

    public void run() {
        brobot.sendSMS("Hey bro, take your creatine. Think about your gains!");
    }
}
