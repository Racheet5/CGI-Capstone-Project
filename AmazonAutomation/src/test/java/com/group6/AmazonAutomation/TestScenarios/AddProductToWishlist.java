package com.group6.AmazonAutomation.TestScenarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.Dimension;

public class AddProductToWishlist {

	  WebDriver driver;
	  JavascriptExecutor js;
	  
	  @BeforeMethod
	  public void beforeMethod() {
		  WebDriverManager.chromedriver().setup();
		  driver=new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		  js = (JavascriptExecutor) driver;
	  }
	  
	  @Test(dataProvider = "dp")
	  public void login(String userEmail, String password) throws InterruptedException, IOException {
		  
		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String reportname="Login-"+timestamp+".html";
		String screenshot="Login"+timestamp+".png";
			
		ExtentReports extent=new ExtentReports();
		ExtentSparkReporter spark=new ExtentSparkReporter(reportname);
		extent.attachReporter(spark);
		ExtentTest test=extent.createTest("Lauch Browser");
		
		driver.get("https://www.amazon.in/");
	    driver.manage().window().setSize(new Dimension(945, 1020));
	    js.executeScript("window.scrollTo(0,0)");
	    driver.findElement(By.cssSelector("#nav-signin-tooltip .nav-action-inner")).click();
	    driver.findElement(By.id("ap_email")).sendKeys(userEmail);
	    driver.findElement(By.cssSelector(".a-button-inner > #continue")).click();
	    driver.findElement(By.id("ap_password")).sendKeys(password);
	    driver.findElement(By.id("signInSubmit")).click();
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		 String userName = driver.findElement(By.id("glow-ingress-block")).findElement(By.tagName("span")).getText();
		 String title = driver.getTitle();
		 System.out.println(title);
		 System.out.println(userName);
		 
	 	TakesScreenshot TS=(TakesScreenshot)driver;
		File sourcefile = TS.getScreenshotAs(OutputType.FILE);
		File destfile = new File("./screenshots/"+screenshot);
		FileUtils.copyFile(sourcefile, destfile);
		 
		 
		 if (userName.equals("Deliver to DEVENDAR"))
		 {
			 System.out.println("Login Successfull");
			 test.pass("pass");
			 System.out.println("./screenshots/"+screenshot);
			 test.addScreenCaptureFromPath("./screenshots/"+screenshot);
			 
		 }
		 else
		 {
			 System.out.println("Login unsuccessfull");
			 test.fail("failed");
			 System.out.println("./screenshots/"+screenshot);
			 test.addScreenCaptureFromPath("./screenshots/"+screenshot);
		 }
		 
		 timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		screenshot="Addtowishlist"+timestamp+".png";
		
		sourcefile = TS.getScreenshotAs(OutputType.FILE);
		destfile = new File("./screenshots/"+screenshot);
		FileUtils.copyFile(sourcefile, destfile);
		 
		 driver.findElement(By.id("twotabsearchtextbox")).click();
	    driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Iphone");
	    driver.findElement(By.id("nav-search-submit-button")).click();
	    driver.findElement(By.linkText("Apple iPhone 13 (128GB) - Green")).click();
	    
	    ArrayList<String> newTb = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(newTb.get(1));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        
        for (String handle : newTb) {
            driver.switchTo().window(handle);
        }
        Thread.sleep(2000);
        driver.close();
        driver.switchTo().window(newTb.iterator().next());
	    
	    driver.findElement(By.id("add-to-wishlist-button")).click();
	    driver.findElement(By.id("atwl-list-name-2SJNCZU173ZS8")).click();
	    driver.findElement(By.linkText("View Your List")).click();
	    String newProductTitle = driver.findElement(By.id("itemName_I3PCM1GHHJ5XJ0")).getAttribute("title");
	    
	    if(newProductTitle.equals("Apple iPhone 13 (128GB) - Green")) {
	    	System.out.println("Product Added to wishlist Successfully");
			 test.pass("pass");
			 System.out.println("./screenshots/"+screenshot);
			 test.addScreenCaptureFromPath("./screenshots/"+screenshot);
	    }else {
	    	System.out.println("Product addition to wishlist is failed");
			 test.pass("pass");
			 System.out.println("./screenshots/"+screenshot);
			 test.addScreenCaptureFromPath("./screenshots/"+screenshot);
	    }
	    
	    
		
		 extent.flush();
					
	}
	  
	  public void afterMethod() {
		  driver.quit();
	  }
	  
	  @DataProvider
	  public String[][] dp() throws IOException {
		  String[][] data=new String[1][2];

		  File file1=new File("./amz_logindata.xlsx");
		  FileInputStream fis=new FileInputStream(file1);
		  XSSFWorkbook workbook=new XSSFWorkbook(fis);
		  XSSFSheet sheet=workbook.getSheetAt(0);
		  int rowcount=sheet.getPhysicalNumberOfRows();
		  System.out.println("row count:"+rowcount);
		  for(int i=0;i<rowcount;i++)
		  {
			  data[i][0]=sheet.getRow(i).getCell(0).getStringCellValue();
			  data[i][1]=sheet.getRow(i).getCell(1).getStringCellValue();
		  }
		  return data;
	    
	  }

}
