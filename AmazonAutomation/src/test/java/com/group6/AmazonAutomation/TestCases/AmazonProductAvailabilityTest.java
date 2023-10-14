package com.group6.AmazonAutomation.TestCases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.Assert;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
//import com.aventstack.extentreports.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.group6.AmazonAutomation.PageObject.AmazonHomepage;
import com.aventstack.extentreports.ExtentTest;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AmazonProductAvailabilityTest {
    private WebDriver driver;
    private AmazonHomepage amazonHomePage;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        extent = new ExtentReports();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportname = "Search-" + timestamp + ".html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportname);
        extent.attachReporter(htmlReporter);

        amazonHomePage = new AmazonHomepage(driver);
        amazonHomePage.navigateToHomePage("https://www.amazon.in/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        extent.flush();
    }

    @Test
    public void testProductAvailability() throws InterruptedException, IOException {
        try {
            String[] searchKeywords = {"mobile", "Laptop", "Headphones", "Pendrive"};

            for (String keyword : searchKeywords) {
                test = extent.createTest("Search Product by Category: " + keyword);
                amazonHomePage.searchForProduct(keyword);
                amazonHomePage.clickOnProductLink();

                ArrayList<String> newTb = new ArrayList(driver.getWindowHandles());
                driver.switchTo().window(newTb.get(1));
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

                boolean isInStock = (Boolean) jsExecutor.executeScript("return document.documentElement.innerText.trim().includes('In stock');");
                if (isInStock) {
                    Assert.assertTrue(isInStock, "The product is in stock.");
                    System.out.println("Product is in stock");

                    String screenshotPath = amazonHomePage.captureAndAttachScreenshot(keyword, driver);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                    test.log(Status.PASS, "Product is in stock for keyword: " + keyword);
                    test.addScreenCaptureFromPath(screenshotPath);
                } else {
                    Assert.assertTrue(isInStock, "The product is not in stock.");
                    System.out.println("Product is not in stock");

                    String screenshotPath = amazonHomePage.captureAndAttachScreenshot(keyword, driver);
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                    test.log(Status.FAIL, "Product is not in stock for keyword: " + keyword);
                    test.addScreenCaptureFromPath(screenshotPath);
                }

                for (String handle : newTb) {
                    driver.switchTo().window(handle);
                }
                System.out.println("Product details page for " + keyword + " loaded: " + driver.getTitle());
                driver.close();
                driver.switchTo().window(newTb.iterator().next());
                driver.navigate().back();
                amazonHomePage.clearSearchBox();
            }
        } catch (Exception e) {
            e.printStackTrace();
            test.log(Status.ERROR, "An error occurred: " + e.getMessage());
        }
    }
}
