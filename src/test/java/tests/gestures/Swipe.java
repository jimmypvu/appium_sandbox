package tests.gestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import jpvu.enums.Directions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Swipe extends BaseTest {
    public By viewsBtnLoc = AppiumBy.accessibilityId("Views");
    public By galleryBtnLoc = AppiumBy.accessibilityId("Gallery");
    public By photosBtnLoc = By.xpath("//android.widget.TextView[@text='1. Photos']");
    public By firstImgLocator = By.xpath("//android.widget.ImageView[1]");
    public By secondImgLocator = By.xpath("//android.widget.ImageView[2]");

    @AndroidFindBy(accessibility = "Views")
    public WebElement viewsBtn;

    @Test
    public void swipeByElement(){
//        driver.findElement(viewsBtnLoc).click();
        viewsBtn.click();
        driver.findElement(galleryBtnLoc).click();
        driver.findElement(photosBtnLoc).click();

        WebElement firstImage = driver.findElement(firstImgLocator);

        Assert.assertEquals(firstImage.getAttribute("focusable"), "true");
        swipeOnElement(firstImage, Directions.LEFT);

        WebElement secondImage = driver.findElement(secondImgLocator);

        Assert.assertEquals(secondImage.getAttribute("focusable"), "true");
        Assert.assertEquals(firstImage.getAttribute("focusable"), "false");

        swipeOnElement(firstImage, Directions.RIGHT);

        Assert.assertEquals(secondImage.getAttribute("focusable"), "false");
        Assert.assertEquals(firstImage.getAttribute("focusable"), "true");
    }

    @Test
    public void swipeByCoordinates(){
        driver.findElement(viewsBtnLoc).click();
        driver.findElement(galleryBtnLoc).click();
        driver.findElement(photosBtnLoc).click();

        WebElement firstImg = driver.findElement(firstImgLocator);
        Assert.assertEquals(firstImg.getAttribute("focusable"), "true");

        swipeByCoord(Directions.LEFT, 600, 350, 300, 350);

        WebElement secondImg = driver.findElement(secondImgLocator);
        Assert.assertEquals(secondImg.getAttribute("focusable"), "true");
        Assert.assertEquals(firstImg.getAttribute("focusable"), "false");

        swipeByCoord(Directions.RIGHT, 350, 350, 300, 350);
        Assert.assertEquals(secondImg.getAttribute("focusable"), "false");
        Assert.assertEquals(firstImg.getAttribute("focusable"), "true");
    }
}
