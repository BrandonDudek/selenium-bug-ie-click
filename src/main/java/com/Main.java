package com;

import org.openqa.selenium.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        final String DRIVER_NAME = "IEDriverServer_x32_3.13.0.exe";

        try {
            Runtime.getRuntime().exec("taskkill /T /F /IM " + DRIVER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(DRIVER_NAME);
        Path path = Files.createTempFile("ie-driver-", ".exe");
        Files.delete(path);
        Files.copy(is, path);
        System.setProperty("webdriver.ie.driver", path.toString());

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.introduceFlakinessByIgnoringSecurityDomains();
        options.destructivelyEnsureCleanSession();
        options.enablePersistentHovering();
        options.ignoreZoomSettings();
        options.setCapability("logLevel", "TRACE");
        final WebDriver DRIVER = new InternetExplorerDriver(options);
        DRIVER.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        DRIVER.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        for(int i = 0; i < 30; i++) {

            DRIVER.get("https://www.google.com/");
            WebElement searchInput = DRIVER.findElement(By.id("lst-ib"));
            searchInput.click(); // ERROR HERE! (usually always, but sometimes intermittent)
            searchInput.sendKeys("Serenity Firefly" + Keys.ESCAPE);

            WebElement searchButton = DRIVER.findElement(By.cssSelector("#tsf input[value='Google Search'][type=submit]"));
            searchButton.click(); // ERROR HERE! (usually always, but sometimes intermittent)
        }

        DRIVER.quit();

        try {
            Runtime.getRuntime().exec("taskkill /T /F /IM " + DRIVER_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(DRIVER_NAME);
    }
}
