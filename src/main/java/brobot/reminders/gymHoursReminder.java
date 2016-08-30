package brobot.reminders;

import brobot.Brobot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by eisenhorn on 8/29/2016.
 */
public class gymHoursReminder extends TimerTask{
    WebDriver driver;
    Brobot brobot;
    int temp;

    public gymHoursReminder(Brobot brobot){
        this.brobot = brobot;
        temp=0;
    }

    private void createNewDriver(){
        driver  = new PhantomJSDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    public void run() {
        temp++;
        createNewDriver();
        driver.get("https://www.facebook.com/BoiseStateRecreation");
        Document doc = Jsoup.parse(driver.getPageSource());
        Element state = doc.getElementsByClass("_1xm9").first();
        System.out.println(state.text());
        Element time = doc.select("span._c24._50f3").first();
        System.out.println(time.text());
        brobot.sendSMS("The gym is " + state.text() + " " + time.text());
        driver.quit();
    }
}
