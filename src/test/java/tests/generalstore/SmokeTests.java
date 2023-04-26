package tests.generalstore;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import jpvu.enums.Directions;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class SmokeTests extends BaseTest {
    @AndroidFindBy(id = "android:id/text1")
    public WebElement countrySel;
    @AndroidFindBy(className = "android.widget.TextView")
    public List<WebElement> countries;
    @AndroidFindBy(xpath = "//android.widget.TextView[@text = 'United States']")
    public WebElement unitedStatesBtn;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    public WebElement nameField;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    public WebElement maleRad;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    public WebElement femaleRad;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    public WebElement shopBtn;


    @Test
    public void launchAppTest(){
        Assert.assertTrue(nameField.isDisplayed());
        nameField.sendKeys("ShyGuy");
        Assert.assertEquals(nameField.getText(), "ShyGuy");

        countrySel.click();

        Assert.assertTrue(countries.get(0).isDisplayed());
        Assert.assertEquals(countries.get(0).getText(), "Afghanistan");

        scrollToEnd();
        Assert.assertEquals(countries.get(countries.size()-1).getText(), "Zimbabwe");

//        scrollToEleByText("Vietnam");
        scrollUpToEleOrTop(AppiumBy.xpath("//android.widget.TextView[@text = 'United States']"));

        unitedStatesBtn.click();

        Assert.assertEquals(countrySel.getText(), "United States");

        shopBtn.click();
        pause();
        Assert.assertTrue(driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")).isDisplayed());
        Assert.assertEquals(driver.findElement(AppiumBy.id("com.androidsample.generalstore:id/toolbar_title")).getText(), "Products");
    }
}
