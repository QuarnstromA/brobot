package com.github.quarnstroma.brobot.reminders;

import com.github.quarnstroma.brobot.SMSSender.SMSSender;

import java.util.TimerTask;

public class creatineReminder extends TimerTask {

    private final SMSSender sms;

    public creatineReminder(){
        sms = SMSSender.getInstance();
    }

    public void run() {
        sms.sendSMS("Hey bro, take your creatine. Think about your gains!");
    }
}
