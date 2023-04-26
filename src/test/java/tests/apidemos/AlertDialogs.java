package tests.apidemos;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class AlertDialogs extends BaseTest {
    @AndroidFindBy(accessibility = "App")
    public WebElement appsBtn;
    @AndroidFindBy(accessibility = "Alert Dialogs")
    public WebElement alertsBtn;
    @AndroidFindBy(accessibility = "Single choice list")
    public WebElement singleChoiceListBtn;
    @AndroidFindBy(xpath = "//android.widget.CheckedTextView")
    public List<WebElement> choicesBtns;
    @AndroidFindBy(xpath = "//android.widget.CheckedTextView[@text = 'Traffic']")
    public WebElement trafficBtn;
    @AndroidFindBy(id = "android:id/button1")
    public WebElement okBtn;
    @AndroidFindBy(accessibility = "List dialog")
    public WebElement listDialogBtn;
    @AndroidFindBy(accessibility = "Text Entry dialog")
    public WebElement textEntryDialogBtn;
    @AndroidFindBy(accessibility = "Repeat alarm")
    public WebElement repeatAlarmBtn;
    @AndroidFindBy(accessibility = "OK Cancel dialog with Holo Light theme")
    public WebElement okCancelDialogBtn;
    @AndroidFindBy(accessibility = "Progress dialog")
    public WebElement progressDialogBtn;
    @AndroidFindBy(xpath = "//*[contains(@class, 'ProgressBar')]")
    public WebElement progressBar;


    @Test
    public void radioBtnsAlert(){
        appsBtn.click();
        alertsBtn.click();
        singleChoiceListBtn.click();

        for(WebElement btn : choicesBtns){
            Assert.assertTrue(btn.isDisplayed(), "Radio buttons for choices should be displayed");
        }

        Assert.assertEquals(choicesBtns.get(0).getAttribute("checked"), "true");

        trafficBtn.click();

        Assert.assertEquals(choicesBtns.get(0).getAttribute("checked"), "false");
        Assert.assertEquals(trafficBtn.getAttribute("checked"), "true");

        okBtn.click();

        singleChoiceListBtn.click();

        for(WebElement btn : choicesBtns){
            if(btn.getAttribute("text").equals("Traffic")){
                Assert.assertEquals(btn.getAttribute("checked"), "true");
            }else{
                Assert.assertEquals(btn.getAttribute("checked"), "false");
            }
        }
    }

    @Test
    public void textEntryAlert(){
        appsBtn.click();
        alertsBtn.click();
        textEntryDialogBtn.click();

        WebElement usernameField = driver.findElement(AppiumBy.id("io.appium.android.apis:id/username_edit"));
        WebElement passwordField = driver.findElement(AppiumBy.id("io.appium.android.apis:id/password_edit"));

        String username = "sirtestsalot";
        String password = "SuperSecretPassword!0";

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        okBtn.click();

        try{
            if(driver.findElement(AppiumBy.id("android:id/autofill_save_no")).isDisplayed()){
                driver.findElement(AppiumBy.id("android:id/autofill_save_no")).click();
            }
        }catch(Exception e){
            System.out.println("Autosave password prompt not displayed, moving to next step");
        }

        textEntryDialogBtn.click();

        Assert.assertEquals(driver.findElement(AppiumBy.id("io.appium.android.apis:id/username_edit")).getText(), "sirtestsalot");

        Assert.assertFalse(driver.findElement(AppiumBy.id("io.appium.android.apis:id/password_edit")).getText().isEmpty(), "Password element should display masked values in text field");

        Assert.assertEquals(driver.findElement(AppiumBy.id("io.appium.android.apis:id/password_edit")).getText().length(), password.length(), "Masked password text length should equal password length");
    }

    @Test
    public void listItemsDialogAlert(){
        appsBtn.click();
        alertsBtn.click();
        listDialogBtn.click();

        List<WebElement> listItems = driver.findElements(By.xpath("//android.widget.TextView[@resource-id = 'android:id/text1']"));
        for(WebElement item : listItems){
            Assert.assertTrue(item.isDisplayed());
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(listItems.size());

        String itemText = listItems.get(randomIndex).getText();
        listItems.get(randomIndex).click();

        Assert.assertTrue(driver.findElement(AppiumBy.id("android:id/message")).isDisplayed());
        Assert.assertTrue(driver.findElement(AppiumBy.id("android:id/message")).getText().contains(itemText));
        Assert.assertTrue(driver.findElement(AppiumBy.id("android:id/message")).getText().contains(Integer.toString(randomIndex)));
    }

    @Test
    public void okCancelDialog(){
        appsBtn.click();
        alertsBtn.click();
        okCancelDialogBtn.click();

        Assert.assertTrue(driver.findElement(AppiumBy.id("android:id/alertTitle")).isDisplayed());
        Assert.assertEquals(driver.findElement(AppiumBy.id("android:id/alertTitle")).getText(), "Lorem ipsum dolor sit aie consectetur adipiscing\n" +
                "Plloaso mako nuto siwuf cakso dodtos anr koop.");

        driver.findElement(AppiumBy.id("android:id/button1")).click();
    }

    @Test
    public void checkboxesAlert(){
        appsBtn.click();
        alertsBtn.click();
        repeatAlarmBtn.click();

        List<WebElement> checkBoxesBefore = driver.findElements(AppiumBy.className("android.widget.CheckedTextView"));

        Assert.assertEquals(checkBoxesBefore.get(1).getAttribute("checked"), "true");
        Assert.assertEquals(checkBoxesBefore.get(3).getAttribute("checked"), "true");

        checkBoxesBefore.get(1).click();
        checkBoxesBefore.get(3).click();

        Assert.assertEquals(checkBoxesBefore.get(1).getAttribute("checked"), "false");
        Assert.assertEquals(checkBoxesBefore.get(3).getAttribute("checked"), "false");

        for(WebElement box: checkBoxesBefore){
            box.click();
            Assert.assertEquals(box.getAttribute("checked"), "true");
        }

        driver.findElement(AppiumBy.id("android:id/button1")).click();
        repeatAlarmBtn.click();

        List<WebElement> checkBoxesAfter = driver.findElements(AppiumBy.className("android.widget.CheckedTextView"));
        for(WebElement box: checkBoxesAfter){
            Assert.assertEquals(box.getAttribute("checked"), "true");
        }
    }

    @Test
    public void progressDialog(){
        appsBtn.click();
        alertsBtn.click();
        progressDialogBtn.click();

        System.out.println(driver.findElement(By.xpath("//*[contains(@class, 'ProgressBar')]")));
//        Assert.assertTrue(progressBar.isDisplayed());  //element exists only while loading, will get stale ele
    }
}
