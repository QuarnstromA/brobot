package com.github.quarnstroma.brobot.reminders;

import com.github.quarnstroma.brobot.SMSSender.SMSSender;

import java.util.TimerTask;

/**
 * Created by eisenhorn on 8/29/2016.
 */
public class creatineReminder extends TimerTask {

    SMSSender sms;

    public creatineReminder(){
        sms = SMSSender.getInstance();
    }

    public void run() {
        sms.sendSMS("Hey bro, take your creatine. Think about your gains!");
    }
}
