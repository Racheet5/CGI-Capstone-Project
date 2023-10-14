package com.group6.AmazonAutomation.TestScenarios;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

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

public class Searchbycategory {
    private WebDriver driver;
    private By searchbox = By.id("twotabsearchtextbox");
    private By searchbutton = By.xpath("//input[@value='Go']");
    private By productLink = By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[5]/div/div/div/div/div/div[2]/div/div/div[1]/h2/a/span");
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.in/");
        Thread.sleep(5000);
        String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportname="search"+timestamp+".html";
        extent = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportname);
        extent.attachReporter(htmlReporter);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        extent.flush();
    }

    @Test
    public void testProductAvailability() throws InterruptedException {
        try {
    		
            String[] searchKeywords = {"mobile", "Laptop", "Headphones", "Pendrive"};

            for (String keyword : searchKeywords) {
                test = extent.createTest("Search Product by Category: " + keyword);
                WebElement searchBox = driver.findElement(searchbox);
                searchBox.sendKeys(keyword);
                WebElement searchButton = driver.findElement(searchbutton);
                searchButton.click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(productLink));
                WebElement productLinkElement = driver.findElement(productLink);
                productLinkElement.click();
                ArrayList<String> newTb = new ArrayList(driver.getWindowHandles());
                driver.switchTo().window(newTb.get(1));
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

                boolean isInStock = (Boolean) jsExecutor.executeScript("return document.documentElement.innerText.trim().includes('In stock');");
                if (isInStock) {
                    Assert.assertTrue(isInStock, "The product is in stock.");
                    System.out.println("Product is in stock");

                    String timestamp = captureAndAttachScreenshot(keyword);
                    Thread.sleep(2000);
                    test.log(Status.PASS, "Product is in stock for keyword: " + keyword);
                    test.addScreenCaptureFromPath("./search/" + keyword + timestamp + ".png");
                    Thread.sleep(1000);
                    
                } else {
                    Assert.assertTrue(isInStock, "The product is not in stock.");
                    System.out.println("Product is not in stock");

                    String timestamp = captureAndAttachScreenshot(keyword);
                    Thread.sleep(2000);
                    test.log(Status.FAIL, "Product is not in stock for keyword: " + keyword);
                    test.addScreenCaptureFromPath("./search/" + keyword + timestamp + ".png");
                    Thread.sleep(1000);
                }

                for (String handle : newTb) {
                    driver.switchTo().window(handle);
                }
                Thread.sleep(2000);
                System.out.println("Product details page for " + keyword + " loaded: " + driver.getTitle());
                driver.close();
                driver.switchTo().window(newTb.iterator().next());
                driver.navigate().back();
                Thread.sleep(2000);
                searchBox = driver.findElement(searchbox);
                searchBox.clear();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            test.log(Status.ERROR, "An error occurred: " + e.getMessage());
        }
    }
   
    private String captureAndAttachScreenshot(String keyword) throws IOException {
	        TakesScreenshot TS=(TakesScreenshot)driver;
			File sourcefile = TS.getScreenshotAs(OutputType.FILE);
	        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	        File destfile = new File("./search/" + keyword + timestamp + ".png");
	        FileUtils.copyFile(sourcefile, destfile);
	        return timestamp;
    }
    
}