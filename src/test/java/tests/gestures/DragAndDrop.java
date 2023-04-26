package tests.gestures;

import io.appium.java_client.AppiumBy;
import jpvu.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DragAndDrop extends BaseTest {
    public By viewsBtnLocator = AppiumBy.accessibilityId("Views");
    public By dragAndDropBtnLocator = AppiumBy.accessibilityId("Drag and Drop");
    public By dragDot1Loc = AppiumBy.id("io.appium.android.apis:id/drag_dot_1");
    public By dragDot2Loc = AppiumBy.id("io.appium.android.apis:id/drag_dot_2");
    public By dragDot3Loc = AppiumBy.id("io.appium.android.apis:id/drag_dot_3");
    public By dragResultTxtLoc = AppiumBy.id("io.appium.android.apis:id/drag_result_text");

    @Test
    public void dragAndDrop(){
        driver.findElement(viewsBtnLocator).click();
        driver.findElement(dragAndDropBtnLocator).click();

        WebElement dragDot1 = driver.findElement(dragDot1Loc);
        dragAndDrop(dragDot1, 651, 705);

        Assert.assertTrue(driver.findElement(dragResultTxtLoc).isDisplayed());
        Assert.assertEquals(driver.findElement(dragResultTxtLoc).getAttribute("text"), "Dropped!");
    }

    @Test
    public void dragAndDrop2(){
        driver.findElement(viewsBtnLocator).click();
        driver.findElement(dragAndDropBtnLocator).click();

        WebElement dragDot2 = driver.findElement(dragDot1Loc);
        dragAndDrop(dragDot2, 1300, 2400);

        Assert.assertTrue(driver.findElement(dragResultTxtLoc).isDisplayed());
        Assert.assertEquals(driver.findElement(dragResultTxtLoc).getAttribute("text"), "No drop");
    }
}
