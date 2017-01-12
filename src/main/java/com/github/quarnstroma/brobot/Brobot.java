package com.github.quarnstroma.brobot;

import com.github.quarnstroma.brobot.reminders.creatineReminder;
import com.github.quarnstroma.brobot.reminders.gymHoursReminder;
import com.github.quarnstroma.brobot.scheduler.Scheduler;

import java.util.*;

class Brobot {

    private static final long TWENTY_FOUR_HOURS = 86400000;


    private Timer timer;
    private Boolean running;

    public Brobot() {
        try {
            timer = new Timer();
            running = true;
        } catch (Exception e) {
            System.err.println("Exception " + e);
            running = false;
        }
    }



    public void run(){
        timer.schedule(new gymHoursReminder(), Scheduler.computeDelayToNextTime(9, 0, 0), TWENTY_FOUR_HOURS);
        timer.schedule(new creatineReminder(), Scheduler.computeDelayToNextTime(21, 0, 0), TWENTY_FOUR_HOURS);
    }


    public boolean isRunning() {
        return running;
    }
}


