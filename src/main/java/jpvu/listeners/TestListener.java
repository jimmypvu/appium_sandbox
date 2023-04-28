package jpvu.listeners;

import io.appium.java_client.android.AndroidDriver;
import jpvu.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context){
        System.out.println(context.getName() + " tests are starting!");
    }

    @Override
    public void onTestStart(ITestResult result){
        System.out.println(result.getName());
        System.out.println(result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result){
        System.out.println(result.getName() + " test passed!");
    }

    @Override
    public void onTestFailure(ITestResult result){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss SSS");
        LocalDateTime time = LocalDateTime.now();
        String timestamp = dtf.format(time);

        File screenshotFile = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);

        try{
            FileUtils.copyFile(screenshotFile, new File(System.getProperty("user.dir") + "\\screenshots\\" + result.getName() + timestamp + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result.getName() + " test failed");
    }

    @Override
    public void onTestSkipped(ITestResult result){
        System.out.println(result.getName() + " test skipped");
    }

    @Override
    public void onFinish(ITestContext context){
        System.out.println(context.getName() + " tests finished!");
    }
}
