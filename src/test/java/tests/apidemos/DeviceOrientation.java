package tests.apidemos;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeviceOrientation extends BaseTest {
    @AndroidFindBy(accessibility = "Preference")
    public WebElement preferenceBtn;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='3. Preference dependencies']")
    public WebElement preferenceDependenciesBtn;
    @AndroidFindBy(id = "android:id/checkbox")
    public WebElement checkbox;
    @AndroidFindBy(xpath = "(//android.widget.RelativeLayout)[2]")
    public WebElement wifiSettingsBtn;

    @Test
    public void switchToLandscape() {
        preferenceBtn.click();
        preferenceDependenciesBtn.click();
        checkbox.click();

        DeviceRotation rotation = new DeviceRotation(0, 0, 90);
        ((AndroidDriver) driver).rotate(rotation);

        ScreenOrientation orientation = ((AndroidDriver) driver).getOrientation();

        Assert.assertEquals(orientation.toString(), "LANDSCAPE");

        wifiSettingsBtn.click();

        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();

        Assert.assertEquals(alertTitle, "WiFi settings", "Alert title should be 'WiFi settings");
    }

    @Test
    public void rotateDevice(){
        ((AndroidDriver) driver).rotate(ScreenOrientation.LANDSCAPE);

        Assert.assertEquals(((AndroidDriver) driver).getOrientation().toString(), "LANDSCAPE");

        scrollToEleByText("Preference");
        preferenceBtn.click();

        preferenceDependenciesBtn.click();
        checkbox.click();
        wifiSettingsBtn.click();

        String alertTitle = driver.findElement(By.id("android:id/alertTitle")).getText();

        Assert.assertEquals(alertTitle, "WiFi settings", "Alert title should be 'WiFi settings");
    }
}
