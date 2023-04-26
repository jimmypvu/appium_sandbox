package tests.gestures;

import io.appium.java_client.AppiumBy;
import jpvu.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LongPress extends BaseTest {

    @Test
    public void longPress() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();

        //longpress ele
        WebElement peopleNamesEle = driver.findElement(By.xpath("//android.widget.TextView[@text='People Names']"));

        //to do mobile gestures like swipe, scroll, long press/touch & hold, etc, need to inject javascript, see appium docs
        //reusable long press method in basetest
        longPress(peopleNamesEle, 2000);

        WebElement popoutMenu = driver.findElement(By.id("android:id/title"));
        String menuItemText = popoutMenu.getText();

        Assert.assertTrue(popoutMenu.isDisplayed(), "Menu should be displayed after long press/touch & hold");
        Assert.assertEquals(menuItemText, "Sample menu", "First menu item text should be 'Sample menu'");
    }
}
