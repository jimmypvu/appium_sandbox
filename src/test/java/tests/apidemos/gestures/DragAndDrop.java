package tests.apidemos.gestures;

import io.appium.java_client.pagefactory.AndroidFindBy;
import jpvu.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DragAndDrop extends BaseTest {
    @AndroidFindBy(accessibility = "Views")
    public WebElement viewsBtn;
    @AndroidFindBy(accessibility = "Drag and Drop")
    public WebElement dragAndDropBtn;
    @AndroidFindBy(id = "io.appium.android.apis:id/drag_dot_1")
    public WebElement dragDot;
    @AndroidFindBy(id = "io.appium.android.apis:id/drag_result_text")
    public WebElement dragResultTxt;


    @Test
    public void dragAndDrop(){
        viewsBtn.click();
        dragAndDropBtn.click();

        dragAndDrop(dragDot, 651, 705);

        Assert.assertTrue(dragResultTxt.isDisplayed());
        Assert.assertEquals(dragResultTxt.getAttribute("text"), "Dropped!");
    }

    @Test
    public void dragAndDrop2(){
        viewsBtn.click();
        dragAndDropBtn.click();

        dragAndDrop(dragDot, 1300, 2400);

        Assert.assertTrue(dragResultTxt.isDisplayed());
        Assert.assertEquals(dragResultTxt.getAttribute("text"), "No drop");
    }
}
