import brobot.Brobot;

/**
 * Created by eisenhorn on 8/28/2016.
 */
public class App {
    public static void main(String args[]) throws InterruptedException {
        Brobot brobot = new Brobot();
        while(brobot.isRunning()){
            brobot.run();
        }
    }
}
