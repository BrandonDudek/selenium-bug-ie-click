import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.Quotes;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        final String DRIVER_NAME = "IEDriverServer_x32_3.12.0.exe";

        try {
            Runtime.getRuntime().exec("taskkill /T /F /IM " + DRIVER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final File IE_DRIVER = new File(Thread.currentThread().getContextClassLoader().getResource(DRIVER_NAME).getFile());
        System.setProperty("webdriver.ie.driver", IE_DRIVER.getAbsolutePath());

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.introduceFlakinessByIgnoringSecurityDomains();
        options.destructivelyEnsureCleanSession();
        options.enablePersistentHovering();
        options.ignoreZoomSettings();
        options.setCapability("logLevel", "TRACE");
        final WebDriver DRIVER = new InternetExplorerDriver(options);
        DRIVER.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        DRIVER.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        for(int i = 0; i < 15; i++) {

            DRIVER.get("https://www.google.com/");
            WebElement searchInput = DRIVER.findElement(By.id("lst-ib"));
            searchInput.click(); // ERROR HERE! (intermittent)
            searchInput.sendKeys("Walla Walla Walla" + Keys.ESCAPE);

            try {
                Thread.sleep(500);
            }
            catch(InterruptedException e) {
                // Ignore.
            }

            WebElement searchButton = DRIVER.findElement(By.cssSelector("#tsf input[value='Google Search']"));
            searchButton.click(); // ERROR HERE! (intermittent)
        }

        DRIVER.quit();

        try {
            Runtime.getRuntime().exec("taskkill /T /F /IM " + DRIVER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
