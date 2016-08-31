package com.github.quarnstroma.brobot;

/**
 * Created by eisenhorn on 8/28/2016.
 */
public class App {
    public static void main(String args[]) throws InterruptedException {
        Brobot brobot = new Brobot();
        System.out.println("Brobot starting...");
        while(brobot.isRunning()){
            brobot.run();
        }
    }
}
