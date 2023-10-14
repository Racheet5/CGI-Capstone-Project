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
//import com.aventstack.extentreports.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import com.aventstack.extentreports.ExtentTest;

import com.group6.AmazonAutomation.PageObject.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
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

public class AmazonLoginTest {
    private WebDriver driver;
    private login loginPage;

    @BeforeMethod
    public void beforeMethod() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        loginPage = new login(driver);
    }

    @Test(dataProvider = "dp")
    public void loginTest(String userEmail, String password) throws InterruptedException, IOException {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportname = "Login-" + timestamp + ".html";
        String screenshot = "Login" + timestamp + ".png";

        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(reportname);
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Login Test");

        loginPage.openAmazonHomepage("https://www.amazon.in/");
        loginPage.clickSignInButton();
        loginPage.enterUserEmail(userEmail);
        loginPage.clickContinueButton();
        loginPage.enterPassword(password);
        loginPage.clickSignInSubmitButton();

        Thread.sleep(2000); // Consider using WebDriverWait for more robust waits.

        String userName = loginPage.getLoggedInUserName();
        String title = driver.getTitle();
        System.out.println(title);
        System.out.println(userName);

        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("./login/" + screenshot);
        FileUtils.copyFile(sourceFile, destFile);

        if (userName.equals("Deliver to DEVENDAR")) {
            System.out.println("Login Successful");
            test.pass("Pass");
            System.out.println("./login/" + screenshot);
            test.addScreenCaptureFromPath("./login/" + screenshot);
        } else {
            System.out.println("Login Unsuccessful");
            test.fail("Failed");
            System.out.println("./login/" + screenshot);
            test.addScreenCaptureFromPath("./login/" + screenshot);
        }

        extent.flush();
        
        
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @DataProvider
    public String[][] dp() throws IOException {
        String[][] data = new String[1][2];

        File file1 = new File("./datafiles/amz_logindata.xlsx");
        FileInputStream fis = new FileInputStream(file1);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("Row count: " + rowCount);
        
        for (int i = 0; i < rowCount; i++) {
            data[i][0] = sheet.getRow(i).getCell(0).getStringCellValue();
            data[i][1] = sheet.getRow(i).getCell(1).getStringCellValue();
        }
        
        return data;
    }
}
