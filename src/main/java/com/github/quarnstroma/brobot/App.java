package com.github.quarnstroma.brobot;
import com.github.quarnstroma.brobot.SMSSender.SMSSender;
import static java.lang.Thread.sleep;

class App {
    public static void main(String args[]) throws InterruptedException
    {
        Brobot brobot = new Brobot();
        System.out.println("Brobot starting...");
        SMSSender smsSend = SMSSender.getInstance();
        try
        {
            brobot.run();
            smsSend.sendSMS("I'm alive.");
            while (brobot.isRunning())
            {
                sleep(1000);//idle
            }
        }
        finally
        {
            smsSend.sendSMS("I'm dead.");
        }
    }
}
