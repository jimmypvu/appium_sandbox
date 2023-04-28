package jpvu;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import jpvu.enums.DirectionToStringConverter;
import jpvu.enums.Directions;
import jpvu.utils.ApkManager;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    private final String APPIUM_MAIN_PATH = "C:/Users/jimmy/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
    private String apkPath = ApkManager.setApkPath();
    public static AppiumDriver driver;
    public AppiumDriverLocalService service;
    public JavascriptExecutor jse;
    public WebDriverWait wait;

    @BeforeSuite
    public void startAppiumServer(){
        service = new AppiumServiceBuilder().withIPAddress("127.0.0.1")
                                            .usingPort(4723)
                                            .withAppiumJS(new File(APPIUM_MAIN_PATH))
                                            .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                                            .withArgument(GeneralServerFlag.LOG_LEVEL, "info").build();
        service.start();
    }

    @BeforeMethod
    public void setupDriver(Method method) throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("pixel4v11");
        options.setAvd("pixel4v11");
        options.setPlatformName("Android");
        options.setPlatformVersion("11");
        options.setAutomationName("UiAutomator2");
        options.setApp(apkPath);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

//        for running on saucelabs cloud
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String timestamp = LocalDateTime.now().format(dtf);
//
//        MutableCapabilities caps = new MutableCapabilities();
//        caps.setCapability("platformName", "Android");
//        caps.setCapability("appium:app", "storage:filename=General-Store.apk"); // The filename of the mobile app
//        caps.setCapability("appium:deviceName", "Google Pixel 4 GoogleAPI Emulator");
//        caps.setCapability("appium:platformVersion", "11.0");
//        caps.setCapability("appium:automationName", "UiAutomator2");
//        MutableCapabilities sauceOptions = new MutableCapabilities();
//        sauceOptions.setCapability("appiumVersion", "2.0.0-beta56");
//        sauceOptions.setCapability("build", "Android Tests: " + timestamp);
//        sauceOptions.setCapability("name", method.getName());
//        caps.setCapability("sauce:options", sauceOptions);
//
//        String sauceUsername = System.getenv("SAUCE_USERNAME");
//        String sauceToken = System.getenv("SAUCE_ACCESS_KEY");
//        URL url = new URL("https://" + sauceUsername + ":" + sauceToken + "@ondemand.us-west-1.saucelabs.com:443/wd/hub");
//        driver = new AndroidDriver(url, caps);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        jse = driver;

        wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(250));

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AfterMethod
    public void quitDriver(ITestResult result){
//        jse.executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));

        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void stopServer(){
        service.stop();
    }


    /*****************************
     * REUSABLE ACTIONS & METHODS
     *****************************/

    public void longPress(WebElement element, int durationMillis){
        jse.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", durationMillis));
    }

    public void scrollToEleByText(String text){
        System.out.println("Scrolling into view element containing text: \""+text+"\"");

        //use google's ui automator and ui selector
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(textContains(\""+text+"\").instance(0));"));

        System.out.println("Found element containing text: \""+text+"\"");

    }

    public void scrollToEnd(){
        System.out.println("Scrolling to end");
        boolean canScrollMore;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 1600,
                    "direction", "down",
                    "percent", 0.75
            ));
            pause(50);
        }while(canScrollMore);

        System.out.println("Done scrolling to end");
    }

    public void scrollToEnd(int leftBound, int topBound, int width, int height, Directions direction){
        System.out.println("Scrolling to end");
        boolean canScrollMore;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", leftBound, "top", topBound, "width", width, "height", height,
                    "direction", DirectionToStringConverter.convert(direction),
                    "percent", 0.75
            ));
        }while(canScrollMore);

        System.out.println("Done scrolling to end");
    }

    public void scrollToTop(){
        System.out.println("Scrolling to top");
        boolean canScrollMore;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 1600, "width", 200, "height", 1600,
                    "direction", "up",
                    "percent", 0.75
            ));
        }while(canScrollMore);
        System.out.println("Done scrolling to top");
    }

    public void scrollDownBy(int scrollHeight){
        System.out.println("Scrolling down by " + scrollHeight);
        boolean canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                "left", 100, "top", 100, "width", 200, "height", 100 + scrollHeight,
                "direction", "down",
                "percent", 0.75
        ));
        System.out.println("Done scrolling down");
    }

    public void scrollUpBy(int scrollHeight){
        System.out.println("Scrolling up by " + scrollHeight);
        boolean canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                "left", 100, "top", scrollHeight, "width", 200, "height", scrollHeight,
                "direction", "up",
                "percent", 0.75
        ));
        System.out.println("Done scrolling up");
    }

    public void scrollToEleOrEnd(WebElement element){
        System.out.println("Scrolling down to look for target element: " + element);
        boolean canScrollMore;
        boolean found = false;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 1600,
                    "direction", "down",
                    "percent", 0.75
            ));

            try{
                if(element.isDisplayed()){
                    canScrollMore = false;
                    System.out.println("Target element found: " + element);
                }
            }catch(Exception e){
//                if(!canScrollMore && !found){
//                    e.printStackTrace();
//                    System.out.println("Scrolled to end, target element was not found: " + element);
//                }else{
//                    System.out.println("Still scrolling for element..");
//                }
            }
        }while(canScrollMore);
    }

    public void scrollToEleOrEnd(By locator){
        System.out.println("Scrolling down to look for element at: " + locator);
        boolean canScrollMore;
        boolean found = false;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 1600,
                    "direction", "down",
                    "percent", .75
            ));

            try{
                if(driver.findElement(locator).isDisplayed()){
                    canScrollMore = false;
                    System.out.println("Found element with locator: " + locator);
                }
            }catch(Exception e){
                if(!canScrollMore && !found){
                    e.printStackTrace();
                    System.out.println("Scrolled to end, could not find element with locator \"" + locator + "\"");
                }else{
                    System.out.println("Still scrolling for element..");
                }
            }
        }while(canScrollMore);
    }

    public void scrollUpToEleOrTop(By locator){
        System.out.println("Scrolling up to look for element at: " + locator);
        boolean canScrollMore;
        boolean found = false;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 1200, "width", 200, "height", 1600,
                    "direction", "up",
                    "percent", .75
            ));

            try{
                if(driver.findElement(locator).isDisplayed()){
                    canScrollMore = false;
                    System.out.println("Found element with locator: " + locator);
                }
            }catch(Exception e){
                if(!canScrollMore && !found){
                    e.printStackTrace();
                    System.out.println("Scrolled to top, could not find element with locator \"" + locator + "\"");
                }else{
                    System.out.println("Still scrolling for element..");
                }
            }
        }while(canScrollMore);
    }

    public void scrollUpToEleOrTop(WebElement element){
        System.out.println("Scrolling up to look for target element: " + element);
        boolean canScrollMore;
        boolean found = false;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 1600, "width", 200, "height", 1600,
                    "direction", "up",
                    "percent", 0.75
            ));

            try{
                if(element.isDisplayed()){
                    canScrollMore = false;
                    System.out.println("Target element found: " + element);
                }
            }catch(Exception e){
                if(!canScrollMore && !found){
                    e.printStackTrace();
                    System.out.println("Scrolled to top, target element was not found: " + element);
                }else{
                    System.out.println("Still scrolling for element..");
                }
            }
        }while(canScrollMore);
    }

    public void swipeOnElement(WebElement element, Directions direction){
        jse.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", element,
                "direction", DirectionToStringConverter.convert(direction),
                "percent", 0.75
        ));
    }

    public void swipeOnElement(WebElement element, String direction){
        jse.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", element,
                "direction", direction,
                "percent", 0.75
        ));
    }

    public void swipeOnElement(WebElement element, String direction, float percent){
        Map<String, Object> swipe = new HashMap<>();
        swipe.put("direction", direction);
        swipe.put("elementId", element);
        swipe.put("percent", percent);

        jse.executeScript("mobile: swipeGesture", swipe);
    }

    public void swipeOnElement(WebElement element, Directions direction, float percent){
        jse.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", element,
                "direction", DirectionToStringConverter.convert(direction),
                "percent", percent
        ));
    }

    public void swipeByCoord(Directions direction, int xCoord, int yCoord, int swipeWidth, int swipeHeight){
        jse.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "left", xCoord, "top", yCoord, "width", swipeWidth, "height", swipeHeight,
                "direction", DirectionToStringConverter.convert(direction),
                "percent", 1.0
        ));
    }

    public void dragAndDrop(WebElement element, int xEndCoord, int yEndCoord){
        jse.executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "endX", xEndCoord,
                "endY", yEndCoord
        ));
    }

    public void pause(){
        try{
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void pause(int millis){
        try{
            Thread.sleep(millis);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
