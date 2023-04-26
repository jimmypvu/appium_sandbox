package tests.apidemos.gestures;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.clipboard.ClipboardContentType;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MiscActions extends BaseTest {   //clipboard, android keys, and app activity demos
    @AndroidFindBy(accessibility = "Preference")
    public WebElement preferenceBtn;
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='3. Preference dependencies']")
    public WebElement preferenceDependenciesBtn;
    @AndroidFindBy(id = "android:id/checkbox")
    public WebElement checkbox;
    @AndroidFindBy(xpath = "(//android.widget.RelativeLayout)[2]")
    public WebElement wifiSettingsBtn;
    @AndroidFindBy(id = "android:id/edit")
    public WebElement wifiNameField;
    @AndroidFindBy(id = "android:id/button1")
    public WebElement okBtn;
    @AndroidFindBy(id = "com.google.android.apps.nexuslauncher:id/hotseat")
    public WebElement homescreenLauncherApps;
    @AndroidFindBy(accessibility = "API Demos")
    public WebElement demoAppIcon;
    @AndroidFindBy(accessibility = "Chrome")
    public WebElement chromeAppIcon;

    @Test
    public void copyPasteText() {
        preferenceBtn.click();
        preferenceDependenciesBtn.click();
        checkbox.click();
        wifiSettingsBtn.click();

        //set clipboard text (or image)
        String someCopiedText = "copied text / wifi name lol";
        ((AndroidDriver) driver).setClipboardText(someCopiedText);

        //get clipboard text
        wifiNameField.sendKeys(((AndroidDriver) driver).getClipboardText());
        okBtn.click();

        wifiSettingsBtn.click();
        Assert.assertEquals(wifiNameField.getText(), someCopiedText);
    }

    @Test
    public void copyPasteText2(){
        //set clipboard text (or image)
        String someCopiedText = "copied text / wifi name lol";
        ((AndroidDriver) driver).setClipboardText(someCopiedText);

        preferenceBtn.click();
        preferenceDependenciesBtn.click();
        checkbox.click();
        wifiSettingsBtn.click();

        //paste clipboard contents
        wifiNameField.click();
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.PASTE));
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));  //Enter can be new line or submit
        okBtn.click();

        wifiSettingsBtn.click();
        Assert.assertEquals(wifiNameField.getText(), someCopiedText + "\n");
    }

    @Test(description = "just playing around with encoder/decoder lol")
    public void copyPasteText3(){
        //set clipboard text (or image)
        String someCopiedText = "copied text / wifi name lol";
        ((AndroidDriver) driver).setClipboard(ClipboardContentType.PLAINTEXT, Base64.getMimeEncoder()
                .encode(someCopiedText.getBytes(StandardCharsets.UTF_8)));

        preferenceBtn.click();
        preferenceDependenciesBtn.click();
        checkbox.click();
        wifiSettingsBtn.click();

        //get clipboard text
        String clipboardText = new String(Base64.getMimeDecoder().decode(((AndroidDriver) driver).getClipboard(ClipboardContentType.PLAINTEXT)));

        //paste clipboard contents
        wifiNameField.sendKeys(clipboardText);
        okBtn.click();

        wifiSettingsBtn.click();
        Assert.assertEquals(wifiNameField.getText(), someCopiedText);
    }

    @Test(enabled = false)
    public void copyPasteImage() throws IOException {
        //encode image to base64 string, don't need to convert to base64 actually for appium clipboard, just byte[]
        BufferedImage image = ImageIO.read(new File("./src/test/resources/images/chibinaruto.png"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStream encodedStream = Base64.getMimeEncoder().wrap(outputStream);

        ImageIO.write(image, "png", encodedStream);
        encodedStream.flush();
        encodedStream.close();

        byte[] imageByteArray = outputStream.toByteArray();

        //set clipboard image, y u no work :[
//        ((AndroidDriver) driver).setClipboard(ClipboardContentType.IMAGE, imageByteArray);

        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));

        Assert.assertTrue(homescreenLauncherApps.isDisplayed());

        chromeAppIcon.click();

        Assert.assertTrue(driver.findElement(AppiumBy.id("com.android.chrome:id/search_box_text")).isDisplayed());

        //get clipboard contents
//        driver.findElement(AppiumBy.id("android:id/edit")).sendKeys(((AndroidDriver) driver).getClipboard(ClipboardContentType.IMAGE));

    }

    @Test(description = "start app from a certain point via Activity")
    public void appActivity(){
        //"adb shell dumpsys window | find "mCurrentFocus"" in cmd/terminal to find current app page
        //pass the app package (* before /) as first arg, activity (* after /) as second arg

        Activity activity = new Activity("io.appium.android.apis", "io.appium.android.apis.preference.PreferenceDependencies");
        ((AndroidDriver) driver).startActivity(activity);

        Assert.assertTrue(checkbox.isDisplayed());

        checkbox.click();
        wifiSettingsBtn.click();
        wifiNameField.sendKeys("app activity test");
        okBtn.click();
        wifiSettingsBtn.click();
        Assert.assertEquals(wifiNameField.getText(), "app activity test");
    }
}
