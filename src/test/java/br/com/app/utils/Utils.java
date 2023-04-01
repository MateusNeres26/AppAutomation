package br.com.app.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {


    public static AppiumDriver<MobileElement> driver;
    public static WebDriverWait wait;
    public static TouchAction touchAction;

    public enum Platform {
        ANDROID, IOS
    }

    public static String getPlatform(){
        String env = System.getProperty("platform"); //Pega
        if (env != null){
            return env.toUpperCase();//maiusculo
        }else {
            Assert.fail("Platform not defined. \n Example: -Dplatform:ANDROID or -Dplatform:IOS");
            return null;
        }
    }

    public static String getEnv(){
        String env = System.getProperty("env");
        if (env != null) {
            return env.toLowerCase(Locale.ROOT);
        } else {
            Assert.fail("Environment not defined. \n Example: -Denv=hml or -Denv=prd");
            return null;
        }
    }

    public static URL getUrlAppium() throws MalformedURLException {
        return new URL("http://127.0.0.1:4723");
    }

    public static void init() throws MalformedURLException{ // criando um método
        Platform platform = Platform.valueOf(getPlatform());//instanciando o getPlatform
        DesiredCapabilities caps = new DesiredCapabilities();//instanciando

        //função de desvio de fluxo
        switch (platform) {
            case IOS:
                caps.setCapability("automationName", "XCUITest");
                caps.setCapability("deviceName", "iPhone 14 Pro");
                caps.setCapability("platformName", "iOS");
                caps.setCapability("platformVersion", "16.2");
                caps.setCapability("autoAcceptAlerts", true);
                caps.setCapability("udid", "E08FE26C-E7DD-40C3-93F6-F001474E8E67");
                caps.setCapability("app", System.getProperty("user.dir") + "/apps/ios/nomedoapp");

                driver = new IOSDriver<>(getUrlAppium(), caps);
                break;
            case ANDROID:
                caps.setCapability("automationName", "UiAutomator2");
                caps.setCapability("platformName", "Android");
                caps.setCapability("app",  System.getProperty("user.dir") + "/apps/android/app.apk");

                driver = new AndroidDriver<>(getUrlAppium(), caps);
                break;
        }
        wait = new WebDriverWait(driver, 30);
        touchAction = new TouchAction(driver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public static void quit() {
        driver.quit();
    }
    public static AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    public static WebDriverWait getDriverWait() {
        return wait;
    }

    public static void assertContains(String expected, String actual) {
        Assert.assertTrue(actual.contains(expected));
    }


}


