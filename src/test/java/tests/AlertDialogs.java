package tests;

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
    //can use page factory pattern on maven build, support for selenium pagefactory wasn't working with appium 8.3 + java 15 or later on gradle without workaround
    @AndroidFindBy(accessibility = "App")
    public WebElement appsBtn;
    @AndroidFindBy(accessibility = "Alert Dialogs")
    public WebElement alertsBtn;
    @AndroidFindBy(accessibility = "List dialog")
    public WebElement listDialogBtn;
    @AndroidFindBy(accessibility = "Single choice list")
    public WebElement singleChoiceListBtn;
    @AndroidFindBy(accessibility = "Text Entry dialog")
    public WebElement textEntryDialogBtn;
    @AndroidFindBy(accessibility = "Repeat alarm")
    public WebElement repeatAlarmBtn;
    @AndroidFindBy(accessibility = "Ok Cancel dialog with Holo Light theme")
    public WebElement okCancelDialogBtn;
    @AndroidFindBy(accessibility = "Progress dialog")
    public WebElement progressDialogBtn;

    public By appsBtnLoc = AppiumBy.accessibilityId("App");
    public By alertsBtnLoc = AppiumBy.accessibilityId("Alert Dialogs");
    public By listDialogBtnLoc = AppiumBy.accessibilityId("List dialog");
    public By singleChoiceListBtnLoc = AppiumBy.accessibilityId("Single choice list");
    public By textEntryDialogBtnLoc = AppiumBy.accessibilityId("Text Entry dialog");
    public By repeatAlarmBtnLoc = AppiumBy.accessibilityId("Repeat alarm");
    public By okCancelDialogBtnLoc = AppiumBy.accessibilityId("OK Cancel dialog with Holo Light theme");
    public By progressDialogBtnLoc = AppiumBy.accessibilityId("Progress dialog");

    @Test
    public void radioBtnsAlert(){
        driver.findElement(appsBtnLoc).click();
        driver.findElement(alertsBtnLoc).click();
        driver.findElement(singleChoiceListBtnLoc).click();

        List<WebElement> choicesBtns = driver.findElements(By.xpath("//android.widget.CheckedTextView"));
        WebElement trafficChoiceBtn = driver.findElement(By.xpath("//android.widget.CheckedTextView[@text = 'Traffic']"));
        WebElement okBtn = driver.findElement(AppiumBy.id("android:id/button1"));

        for(WebElement btn : choicesBtns){
            Assert.assertTrue(btn.isDisplayed(), "Radio buttons for choices should be displayed");
        }

        Assert.assertEquals(choicesBtns.get(0).getAttribute("checked"), "true");

        trafficChoiceBtn.click();

        Assert.assertEquals(choicesBtns.get(0).getAttribute("checked"), "false");
        Assert.assertEquals(trafficChoiceBtn.getAttribute("checked"), "true");

        okBtn.click();

        driver.findElement(singleChoiceListBtnLoc).click();
        List<WebElement> choicesBtnsAfterReopened = driver.findElements(By.xpath("//android.widget.CheckedTextView"));

        for(WebElement btn : choicesBtnsAfterReopened){
            if(!btn.getAttribute("text").contains("Traffic")){
                Assert.assertEquals(btn.getAttribute("checked"), "false");
            }else{
                Assert.assertEquals(btn.getAttribute("checked"), "true");
            }
        }
    }

    @Test
    public void textEntryAlert(){
        driver.findElement(appsBtnLoc).click();
        driver.findElement(alertsBtnLoc).click();
        driver.findElement(textEntryDialogBtnLoc).click();

        WebElement usernameField = driver.findElement(AppiumBy.id("io.appium.android.apis:id/username_edit"));
        WebElement passwordField = driver.findElement(AppiumBy.id("io.appium.android.apis:id/password_edit"));
        WebElement okBtn = driver.findElement(AppiumBy.id("android:id/button1"));

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

        driver.findElement(textEntryDialogBtnLoc).click();

        Assert.assertEquals(driver.findElement(AppiumBy.id("io.appium.android.apis:id/username_edit")).getText(), "sirtestsalot");

        Assert.assertTrue(!driver.findElement(AppiumBy.id("io.appium.android.apis:id/password_edit")).getText().isEmpty(), "Password element should have encrypted value in text attribute");

        Assert.assertEquals(driver.findElement(AppiumBy.id("io.appium.android.apis:id/password_edit")).getText().length(), password.length(), "Masked password text length should equal password length");
    }

    @Test
    public void listItemsDialogAlert(){
        driver.findElement(appsBtnLoc).click();
        driver.findElement(alertsBtnLoc).click();
        driver.findElement(listDialogBtnLoc).click();

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
        driver.findElement(appsBtnLoc).click();
        driver.findElement(alertsBtnLoc).click();
        driver.findElement(okCancelDialogBtnLoc).click();

        Assert.assertTrue(driver.findElement(AppiumBy.id("android:id/alertTitle")).isDisplayed());
        Assert.assertEquals(driver.findElement(AppiumBy.id("android:id/alertTitle")).getText(), "Lorem ipsum dolor sit aie consectetur adipiscing\n" +
                "Plloaso mako nuto siwuf cakso dodtos anr koop.");

        driver.findElement(AppiumBy.id("android:id/button1")).click();
    }

    @Test
    public void checkboxesAlert(){
        driver.findElement(appsBtnLoc).click();
        driver.findElement(alertsBtnLoc).click();
        driver.findElement(repeatAlarmBtnLoc).click();

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
        driver.findElement(repeatAlarmBtnLoc).click();

        List<WebElement> checkBoxesAfter = driver.findElements(AppiumBy.className("android.widget.CheckedTextView"));
        for(WebElement box: checkBoxesAfter){
            Assert.assertEquals(box.getAttribute("checked"), "true");
        }
    }

    @Test
    public void progressDialog(){
        driver.findElement(appsBtnLoc).click();
        driver.findElement(alertsBtnLoc).click();
        driver.findElement(progressDialogBtnLoc).click();

        System.out.println(driver.findElement(By.xpath("//*[contains(@class, 'ProgressBar')]")));
//        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@class, 'ProgressBar')]")).isDisplayed());
    }
}
