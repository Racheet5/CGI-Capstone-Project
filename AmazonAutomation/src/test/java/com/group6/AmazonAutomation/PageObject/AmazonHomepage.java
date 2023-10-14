package com.group6.AmazonAutomation.PageObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonHomepage {
    private WebDriver driver;
    private By searchbox = By.id("twotabsearchtextbox");
    private By searchbutton = By.xpath("//input[@value='Go']");
    private By productLink = By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[5]/div/div/div/div/div/div[2]/div/div/div[1]/h2/a/span");

    public AmazonHomepage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage(String url) {
        driver.get(url);
    }

    public void searchForProduct(String keyword) {
        WebElement searchBox = driver.findElement(searchbox);
        searchBox.sendKeys(keyword);
        WebElement searchButton = driver.findElement(searchbutton);
        searchButton.click();
    }

    public void clickOnProductLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(productLink));
        WebElement productLinkElement = driver.findElement(productLink);
        productLinkElement.click();
    }

	public void clearSearchBox() {
		// TODO Auto-generated method stub
		
	}

	public String captureAndAttachScreenshot(String keyword, WebDriver driver2) throws IOException {
		TakesScreenshot TS=(TakesScreenshot)driver;
        File sourcefile = TS.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        File destfile = new File("./search/" + keyword + timestamp + ".png");
        FileUtils.copyFile(sourcefile, destfile);
        return "./search/" + keyword + timestamp + ".png";
	}
}
