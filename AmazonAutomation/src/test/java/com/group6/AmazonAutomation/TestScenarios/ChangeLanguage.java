package com.group6.AmazonAutomation.TestScenarios;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChangeLanguage {
	   
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
	  
	  
	  @Test
	  public void changeCountry() throws IOException, InterruptedException{
		  
		  String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			String reportname="lang-"+timestamp+".html";
			String screenshot="lang"+timestamp+".png";
				
			ExtentReports extent=new ExtentReports();
			ExtentSparkReporter spark=new ExtentSparkReporter(reportname);
			extent.attachReporter(spark);
			ExtentTest test=extent.createTest("Lauch Browser");
		  
			driver.get("https://www.amazon.in/");
			Thread.sleep(3000);
			driver.findElement(By.id("icp-nav-flyout")).click();
			WebElement radio_btn = driver.findElement(By.xpath("//input[@type=\"radio\" and @name=\"lop\" and @value=\"hi_IN\"]"));
			js.executeScript("arguments[0].click();", radio_btn);
			driver.findElement(By.xpath("//*[@id=\"icp-save-button\"]/span/input")).click();
		
			Thread.sleep(3000);
			String updatedLang = driver.findElement(By.xpath("//*[@id=\"icp-nav-flyout\"]/span/span[2]/div")).getText();
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
			 
			 Thread.sleep(3000);
			 
			this.afterMethod();
	  }
	  
	  @AfterMethod
	  public void afterMethod() {
		  driver.quit();
	  }
	  
}
