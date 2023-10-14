package com.group6.AmazonAutomation.TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CheckoutProcess {
	WebDriver driver;
	  JavascriptExecutor js;
	  
	  @BeforeMethod
	  public void beforeMethod() {
		  WebDriverManager.chromedriver().setup();
		  driver=new ChromeDriver();
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		  js = (JavascriptExecutor) driver;
	  }
	
	By loginButton=By.id("nav-link-accountList");
	By emailField=By.id("ap_email");
	By continueButton=By.id("continue");
	By passwordField=By.id("ap_password");
	By loginSubmitButton=By.id("signInSubmit");
	By cartButton=By.id("nav-cart");
	By checkoutButton=By.xpath("//input[@data-feature-id=\"proceed-to-checkout-action\"]");
	By addressButton=By.xpath("//input[@data-testid=\"Address_selectShipToThisAddress\"]");
	By paymentButton=By.xpath("(//input[@name=\"ppw-widgetEvent:SetPaymentPlanSelectContinueEvent\"])[1]");
	By orderButton=By.id("bottomSubmitOrderButtonId-announce");
	By messageLabel=By.xpath("//h4[contains(text(), \"Order placed, thank you!\")]");
	

	
	@Test(dataProvider = "dp") 
public void f(String n, String s) throws InterruptedException, IOException {
		
	  driver.get("https://www.amazon.in/");
	  Thread.sleep(3000);
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  
	  String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	  String reportname="Checkout"+timestamp+".html";
	  String screenshot="Checkout"+timestamp+".png";
	  
	  ExtentReports extent = new ExtentReports();
	  ExtentSparkReporter spark = new ExtentSparkReporter(reportname);
	  extent.attachReporter(spark);
	  ExtentTest test = extent.createTest("Place an Order");
	  
	  
	  driver.findElement(loginButton).click();
	  Thread.sleep(1000);
	  driver.findElement(emailField).sendKeys(n);
	  Thread.sleep(1000);
	  driver.findElement(continueButton).click();
	  Thread.sleep(1000);
	  driver.findElement(passwordField).sendKeys(s);
	  Thread.sleep(1000);
	  driver.findElement(loginSubmitButton).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    
    if (driver.findElement(By.id("nav-link-accountList")).isDisplayed()) {
        System.out.println("Login successful!");
    } else {
        System.out.println("Login failed!");
    }
    
    driver.findElement(cartButton).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    
    
    driver.findElement(checkoutButton).click();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    
    
    driver.findElement(addressButton).click();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);      
    
    // Option for payment
    Thread.sleep(5000);
    JavascriptExecutor js =(JavascriptExecutor)driver;
    js.executeScript("window.scrollBy(0,300)");
    Thread.sleep(5000);
    driver.findElement(paymentButton).click();
    Thread.sleep(3000);
    WebElement placeOrder = driver.findElement(orderButton);
    
     TakesScreenshot TS=(TakesScreenshot)driver;
	  File sourcefile = TS.getScreenshotAs(OutputType.FILE);
	  File destfile = new File("./checkout/"+screenshot);
	  FileUtils.copyFile(sourcefile, destfile);
	  
    
    if(placeOrder.isDisplayed()) {
        System.out.println("Checkout Process done");
        test.pass("Checkout Process done");
        test.addScreenCaptureFromPath("./checkout/"+screenshot);
     }else {
        System.out.println("Checkout Process failed");
        test.fail("Checkout Process failed");
        test.addScreenCaptureFromPath("./checkout/"+screenshot);
    }
    	extent.flush();
    	Thread.sleep(2000);
    	driver.quit();
	}
    

	@DataProvider
	public String[][] dp() throws IOException {
		  String[][] data=new String[1][2];
		  File file1=new File("./datafiles/checkoutdata.xlsx");
		  FileInputStream fis=new FileInputStream(file1);
		  XSSFWorkbook workbook=new XSSFWorkbook(fis);
		  XSSFSheet sheet=workbook.getSheetAt(0);
		  int rowcount=sheet.getPhysicalNumberOfRows();
		  
			  data[0][0]=sheet.getRow(0).getCell(0).getStringCellValue();
	
			  data[0][1]=sheet.getRow(0).getCell(1).getStringCellValue();
	
		  return data;
	}


	@AfterMethod
	public void afterMethod() {
		this.driver.quit();
	}
}

