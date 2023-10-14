package com.group6.AmazonAutomation.TestCases;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.group6.AmazonAutomation.PageObject.AmazonLanguage;

public class LanguageChange {
    private WebDriver driver;
    private AmazonLanguage amazonLanguage;

    @BeforeMethod
    public void beforeMethod() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        amazonLanguage = new AmazonLanguage(driver);
    }

    @Test
    public void changeCountry() throws IOException, InterruptedException {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportname = "LanguageChange-" + timestamp + ".html";
        String screenshot = "LanguageChange" + timestamp + ".png";
        
        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(reportname);
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Lauch Browser");

        amazonLanguage.navigateToHomePage("https://www.amazon.in/");
        Thread.sleep(3000);
        amazonLanguage.clickLanguagePreference();
        amazonLanguage.selectLanguage("hi_IN");
        amazonLanguage.saveLanguagePreference();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(3000);
        String updatedLang = amazonLanguage.getCurrentLanguage();
        System.out.println(updatedLang);
        
        TakesScreenshot TS=(TakesScreenshot)driver;
		File sourcefile = TS.getScreenshotAs(OutputType.FILE);
		File destfile = new File("./langchange/"+screenshot);
		FileUtils.copyFile(sourcefile, destfile);
		 
		 if (updatedLang.equals("HI")){
			 System.out.println("Language changed Successfully");
			 test.pass("pass");
			 System.out.println("./langchange/"+screenshot);
			 test.addScreenCaptureFromPath("./langchange/"+screenshot);
			 
		 }else{
			 System.out.println("Language change failed");
			 test.fail("failed");
			 System.out.println("./langchange/"+screenshot);
			 test.addScreenCaptureFromPath("./langchange/"+screenshot);
		 }
        

        extent.flush();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

}