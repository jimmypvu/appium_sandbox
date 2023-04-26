package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class PageFactoryTest extends BaseTest {
    @AndroidFindBy(accessibility = "Views")
    public WebElement viewsBtn;
    @AndroidFindBy(accessibility = "Gallery")
    public WebElement galleryBtn;

    //page factory in appium works with maven workaround!
    @Test
    public void scrollInLandscapeTest(){
        DeviceRotation landscape = new DeviceRotation(0, 0, 90);
        ((AndroidDriver) driver).rotate(landscape);

        scrollToEleByText("Views");
        viewsBtn.click();
        scrollToEleByText("Gallery");
        galleryBtn.click();

    }
}
