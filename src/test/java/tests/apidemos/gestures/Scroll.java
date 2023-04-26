package tests.apidemos.gestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Scroll extends BaseTest {
    @AndroidFindBy(accessibility = "Views")
    public WebElement viewsBtn;

    @Test
    public void scrollToEleByText(){
        viewsBtn.click();

        //reusable scroll method in basetest, better to scroll by text if you know what you want to scroll to
        scrollToEleByText("WebView");

        Assert.assertTrue(driver.findElement(AppiumBy.accessibilityId("WebView")).isDisplayed(), "Scrolled to element should be visible");
    }

    @Test
    public void scrollByCoordinates(){
        viewsBtn.click();

        //scroll by coordinates if you dont know where you need to scroll to or if you dont know whether or not element exists
        scrollDownBy(1200);
        scrollUpBy(800);
        scrollToEnd();
        scrollToTop();

        By webviewLocator = AppiumBy.accessibilityId("System UI Visibility");

        scrollToEleOrEnd(webviewLocator);

        Assert.assertTrue(driver.findElement(webviewLocator).isDisplayed(), "Scrolled to element should be visible");
    }
}
