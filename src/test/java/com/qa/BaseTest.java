package com.qa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    //Declare ThreadLocal Driver (ThreadLocalMap) for ThreadSafe Tests
    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();

    @BeforeMethod(alwaysRun = true)
    @Parameters(value={"browser"})
    public void setup(String browser) throws MalformedURLException {
       DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("browserName",browser);
        //Set the Hub url (Docker exposed hub URL)
        driver.set(new RemoteWebDriver(new URL("http://3.134.105.184:4444/wd/hub"), desiredCapabilities));

    }

    public WebDriver getDriver() {
        //Get driver from ThreadLocalMap
        return driver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        getDriver().quit();
    }

    @AfterClass(alwaysRun = true)
    void terminate () {
        //Remove the ThreadLocalMap element
        driver.remove();
    }



}
