import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ////////// Create Driver and Launch Browser //////////
        final File IE_DRIVER = new File(Thread.currentThread().getContextClassLoader().getResource("IEDriverServer_x32_3.13.0.exe").getFile());
        System.setProperty("webdriver.ie.driver", IE_DRIVER.getAbsolutePath());

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.introduceFlakinessByIgnoringSecurityDomains();
        options.destructivelyEnsureCleanSession();
        options.enablePersistentHovering();
        options.ignoreZoomSettings();
        final WebDriver DRIVER = new InternetExplorerDriver(options);
        DRIVER.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        DRIVER.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        DRIVER.get("https://www.google.com/");

        for(int i = 0; i < 100; i++) {

            WebElement searchInput = DRIVER.findElement(By.id("lst-ib"));
            searchInput.click();
            searchInput.sendKeys("WALLA " + Keys.ESCAPE);

            WebElement searchButton = DRIVER.findElement(By.cssSelector("#tsf input[value='Google Search']"));
            searchButton.click();
        }

        DRIVER.quit();
    }
}
