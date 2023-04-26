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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    private final String APPIUM_MAIN_PATH = "C:/Users/jimmy/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";
    private final String PATH_APP_1 = System.getProperty("user.dir") + "\\src\\test\\resources\\apks\\ApiDemos-debug.apk";
    public AppiumDriver driver;
    public AppiumDriverLocalService service;
    JavascriptExecutor jse;

    @BeforeSuite
    public void startAppiumServer(){
        service = new AppiumServiceBuilder().withIPAddress("127.0.0.1")
                                            .usingPort(4723)
                                            .withAppiumJS(new File(APPIUM_MAIN_PATH))
                                            .withArgument(GeneralServerFlag.SESSION_OVERRIDE).build();

        service.start();
    }

    @BeforeMethod
    public void setupDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("pixel4v11");
        options.setAvd("pixel4v11");
        options.setApp(PATH_APP_1);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        jse = driver;

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AfterMethod
    public void quitDriver(){
        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void stopServer(){
        service.stop();
    }

    /********************************
     * REUSABLE ACTIONS & METHODS
     *******************************/

    public void longPress(WebElement element, int durationMilliseconds){
        jse.executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", durationMilliseconds));
    }

    public void scrollToEleByText(String text){
        //instantiate google's ui automator and scroll selector
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
    }

    public void scrollToEnd(){
        System.out.println("Scrolling to end");
        boolean canScrollMore;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 1600,
                    "direction", "down",
                    "percent", 1.0
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
                    "percent", 1.0
            ));
        }while(canScrollMore);
        System.out.println("Done scrolling to top");
    }

    public void scrollDownBy(int scrollHeight){
        System.out.println("Scrolling down by " + scrollHeight);
        boolean canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                "left", 100, "top", 100, "width", 200, "height", 100 + scrollHeight,
                "direction", "down",
                "percent", 1.0
        ));
        System.out.println("Done scrolling down");
    }

    public void scrollUpBy(int scrollHeight){
        System.out.println("Scrolling up by " + scrollHeight);
        boolean canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                "left", 100, "top", scrollHeight, "width", 200, "height", scrollHeight,
                "direction", "up",
                "percent", 1.0
        ));
        System.out.println("Done scrolling up");
    }

    public void scrollToEleOrEnd(WebElement element){
        System.out.println("Scrolling to look for target element: " + element);
        boolean canScrollMore;
        boolean found = false;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 1200,
                    "direction", "down",
                    "percent", 1.0
            ));

            try{
                if(element.isDisplayed()){
                    canScrollMore = false;
                    System.out.println("Target element found: " + element);
                }
            }catch(Exception e){
                if(!canScrollMore && !found){
                    e.printStackTrace();
                    System.out.println("Scrolled to end, target element was not found: " + element);
                }else{
                    System.out.println("Still scrolling for element..");
                }
            }
        }while(canScrollMore);
    }

    public void scrollToEleOrEnd(By locator){
        System.out.println("Scrolling to look for element at: " + locator);
        boolean canScrollMore;
        boolean found = false;

        do{
            canScrollMore = (Boolean) jse.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 1200,
                    "direction", "down",
                    "percent", 1.0
            ));

            try{
                if(driver.findElement(locator).isDisplayed()){
                    canScrollMore = false;
                    System.out.println("Found element with locator: " + locator);
                }
            }catch(Exception e){
                if(!canScrollMore && !found){
                    e.printStackTrace();
                    System.out.println("Scrolled to end, element with locator \"" + locator + "\"" + " was not found");
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

    public void swipeOnElement(WebElement element, Directions direction, float percentage){
        jse.executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", element,
                "direction", DirectionToStringConverter.convert(direction),
                "percent", percentage
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
