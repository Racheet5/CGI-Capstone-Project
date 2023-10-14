package com.group6.AmazonAutomation.TestCases;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class Add_to_cart {
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
	
		 By search = By.xpath("//input[@placeholder=\"Search Amazon.in\"]");
		 By searchButton = By.xpath("//input[@id=\"nav-search-submit-button\"]");
		 By Product = By.xpath("//div[@data-index=\"4\"]");
		 By ProductName = By.xpath("//span[@id=\"productTitle\"]");
		 By addToCart = By.xpath("//input[@id=\"add-to-cart-button\"]");
		 By goToCart = By.xpath("//input[@aria-labelledby=\"attach-sidesheet-view-cart-button-announce\"]"); 
		 By AddedProduct = By.xpath("//span[@class=\"a-truncate-cut\"]");
		 By Captcha = By.xpath("//i[@class=\"a-icon a-icon-alert\"]");

		 
	 @Test(dataProvider = "dp")
	 public void AddtoCart(String n, String productName) throws InterruptedException, IOException {
		 
		  driver.get("https://www.amazon.in");
		  
		  String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		  String reportname="AddCart-"+timestamp+".html";
		  String screenshot="AddCart"+timestamp+".png";
		  
		  ExtentReports extent = new ExtentReports();
		  ExtentSparkReporter spark = new ExtentSparkReporter(reportname);
		  extent.attachReporter(spark);
		  ExtentTest test = extent.createTest("Added to cart ");
		  
		  Thread.sleep(3000);
		  driver.findElement(search).sendKeys(productName);
		  Thread.sleep(3000);
	      driver.findElement(searchButton).click();
	      Thread.sleep(3000);
		  driver.findElement(By.linkText("Acer Aspire Lite 11th Gen Intel Core i3 Premium Metal Laptop (8GB RAM/512GB SSD/Windows 11 Home) AL15-51, 39.62cm (15.6\") Full HD Display, Metal Body, Steel Gray, 1.59 Kg")).click();
		  Thread.sleep(3000);
		  JavascriptExecutor js = (JavascriptExecutor) driver;
		  String parent = driver.getWindowHandle();
		  Set<String> so = driver.getWindowHandles();
		  Iterator<String> I1 = so.iterator();
		  while(I1.hasNext()) {
			  String child_window = I1.next();
			  
			  if(!parent.equals(child_window)) {
				  driver.switchTo().window(child_window);
			  }
		  }
		  js.executeScript("window.scrollBy(0,250)"," ");
		  driver.findElement(addToCart).click();
		  driver.findElement(goToCart).click();
		  Boolean added = driver.findElement(AddedProduct).isDisplayed();
		  Assert.assertEquals(added, true);
		  
		  
		  TakesScreenshot ts = (TakesScreenshot)driver;
		  File sourcefile = ts.getScreenshotAs(OutputType.FILE);
		  File destfile = new File("./addtocart/"+screenshot);
		  
		  try {
			  FileUtils.copyFile(sourcefile, destfile);
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
		  
		  if(added.equals(true)) {
			  test.pass("Added to cart page is displayed");
			  test.addScreenCaptureFromPath("./addtocart/"+screenshot);
			  System.out.println("added to cart successfully");
		  }else {
			  System.out.println("Not added to cart");
			  test.fail("not added to cart");
		  }
		  extent.flush();
			  
		  Thread.sleep(3000);
		  driver.quit();
	 }

	 @DataProvider
	 public String[][] dp() throws IOException {
		  String[][] data=new String[1][2];
		  File file1=new File("./datafiles/products.xlsx");
		  FileInputStream fis=new FileInputStream(file1);
		  XSSFWorkbook workbook=new XSSFWorkbook(fis);
		  XSSFSheet sheet=workbook.getSheetAt(0);
		  int rowcount=sheet.getPhysicalNumberOfRows();
		  for(int i=0;i<rowcount;i++)
		  {
			  data[i][0]=sheet.getRow(i).getCell(0).getStringCellValue();
			  data[i][1]=sheet.getRow(i).getCell(1).getStringCellValue();
		  }
		  return data;
	 }
}
