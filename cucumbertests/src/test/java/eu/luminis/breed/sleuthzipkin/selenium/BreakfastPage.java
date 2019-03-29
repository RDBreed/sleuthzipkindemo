package eu.luminis.breed.sleuthzipkin.selenium;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.util.Iterator;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class BreakfastPage {

    private final ChromeDriver chromeDriver;
    private static final String URL = "http://localhost:8080/";

    public BreakfastPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public void navigateToPage() {
        chromeDriver.navigate().to(URL);
        chromeDriver.executeScript("document.body.style.zoom = '75%'");
    }

    public void toastBread() throws InterruptedException {
        chromeDriver.findElementById("toast-button").sendKeys(Keys.RETURN);
        await().atMost(10, SECONDS).until(() -> chromeDriver.findElementsByClassName("toasted").size() > 0);
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementByXPath("//div[@class='toaster-modal show']"));
        Thread.sleep(2000);
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementById("toast-bread"));
    }

    public void makeCoffee() throws InterruptedException {
        chromeDriver.findElementById("start-coffee").sendKeys(Keys.RETURN);
        await().atMost(5, SECONDS).until(() -> chromeDriver.findElementById("coffee-cup").getAttribute("src").contains("full"));
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementById("coffee-cup"));
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementByXPath("//div[@class='toaster-modal show']"));
        Thread.sleep(2000);
    }

    public void fryEgg() throws InterruptedException {
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementById("egg"));
        await().atMost(5, SECONDS).until(() -> hasElementInitialStyle("egg-on-bread"));
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementByXPath("//div[@class='toaster-modal show']"));
        Thread.sleep(1000);
    }

    public void fryBacon() throws InterruptedException {
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementById("bacon"));
        await().atMost(5, SECONDS).until(() -> hasElementInitialStyle("bacon-on-bread"));
        chromeDriver.executeScript("arguments[0].click();", chromeDriver.findElementByXPath("//div[@class='toaster-modal show']"));
        Thread.sleep(1000);
    }

    public String getConversationId() throws JSONException {
        LogEntries logs = chromeDriver.manage().logs().get("performance");
        for (Iterator<LogEntry> it = logs.iterator(); it.hasNext(); ) {
            LogEntry entry = it.next();
            JSONObject json = new JSONObject(entry.getMessage());
            JSONObject message = json.getJSONObject("message");
            if (json.toString().contains("X-B3-CONVID")) {
                JSONObject params = message.getJSONObject("params");
                JSONObject response = params.getJSONObject("response");
                JSONObject headers = response.getJSONObject("headers");
                if (headers.has("Content-Type") && headers.getString("Content-Type").contains("json")) {
                    return headers.getString("X-B3-CONVID");
                }

            }
        }
        throw new RuntimeException("Conversation id not found!");
    }

    public boolean hasBreakfast() throws InterruptedException {
        Thread.sleep(1000);
        return hasElementInitialStyle("bacon-on-bread") && hasElementInitialStyle("egg-on-bread") && hasElementInitialStyle("brown-bread") && hasElementInitialStyle("full-coffee-cup");
    }

    private boolean hasElementInitialStyle(String elementId) {
        return chromeDriver.findElementById(elementId).getAttribute("style").contains("initial");
    }

    public void close() {
        chromeDriver.quit();
    }
}
