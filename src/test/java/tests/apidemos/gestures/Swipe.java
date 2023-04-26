package tests.apidemos.gestures;

import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import jpvu.enums.Directions;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class Swipe extends BaseTest {
    @AndroidFindBy(accessibility = "Views")
    public WebElement viewsBtn;
    @AndroidFindBy(accessibility = "Gallery")
    public WebElement galleryBtn;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='1. Photos']")
    public WebElement photosBtn;
    @AndroidFindBy(xpath = "//android.widget.ImageView[1]")
    public WebElement firstImg;
    @AndroidFindBy(xpath = "//android.widget.ImageView[2]")
    public WebElement secondImg;

    @Test
    public void swipeByElement(){
        viewsBtn.click();
        galleryBtn.click();
        photosBtn.click();

        Assert.assertEquals(firstImg.getAttribute("focusable"), "true");
        swipeOnElement(firstImg, Directions.LEFT);

        Assert.assertEquals(secondImg.getAttribute("focusable"), "true");
        Assert.assertEquals(firstImg.getAttribute("focusable"), "false");

        swipeOnElement(firstImg, Directions.RIGHT);

        Assert.assertEquals(secondImg.getAttribute("focusable"), "false");
        Assert.assertEquals(firstImg.getAttribute("focusable"), "true");
    }

    @Test
    public void swipeByCoordinates(){
        viewsBtn.click();
        galleryBtn.click();
        photosBtn.click();

        Assert.assertEquals(firstImg.getAttribute("focusable"), "true");

        swipeByCoord(Directions.LEFT, 600, 350, 300, 350);

        Assert.assertEquals(secondImg.getAttribute("focusable"), "true");
        Assert.assertEquals(firstImg.getAttribute("focusable"), "false");

        swipeByCoord(Directions.RIGHT, 350, 350, 300, 350);

        Assert.assertEquals(secondImg.getAttribute("focusable"), "false");
        Assert.assertEquals(firstImg.getAttribute("focusable"), "true");
    }

    @Test
    public void swipeTest3(){
        viewsBtn.click();
        galleryBtn.click();
        photosBtn.click();

        Map<String, Object> swipe = new HashMap<>();
        swipe.put("direction", "left");
        swipe.put("elementId", firstImg);
        swipe.put("percent", 0.75);

        jse.executeScript("mobile: swipeGesture", swipe);
    }
}
