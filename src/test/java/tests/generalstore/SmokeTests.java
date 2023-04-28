package tests.generalstore;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import jpvu.enums.Directions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
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
    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    public WebElement shopTitleHeader;


    @Test(description = "general store app - fill form test")
    public void fillForm(){
        wait.until(ExpectedConditions.visibilityOf(nameField));

        Assert.assertTrue(nameField.isDisplayed());

//        nameField.sendKeys("Shy Guy");
//
//        Assert.assertEquals(nameField.getText(), "Shy Guy");

//        ((AndroidDriver) driver).hideKeyboard();
//        femaleRad.click();
//
//        Assert.assertEquals(femaleRad.getAttribute("checked"), "true");
//
//        countrySel.click();
//
//        Assert.assertTrue(countries.get(0).isDisplayed());
//        Assert.assertEquals(countries.get(0).getText(), "Afghanistan");
//
//        scrollToEleByText("Swede");
//        scrollToEnd();
//
//        Assert.assertEquals(countries.get(countries.size()-1).getText(), "Zimbabwe");
//
//        scrollUpToEleOrTop(unitedStatesBtn);
//        unitedStatesBtn.click();
//
//        Assert.assertEquals(countrySel.getText(), "United States");
//
//        shopBtn.click();
//
//        wait.until(ExpectedConditions.visibilityOf(shopTitleHeader));
//
//        Assert.assertTrue(shopTitleHeader.isDisplayed());
//        Assert.assertEquals(shopTitleHeader.getText(), "Products");
    }


}
