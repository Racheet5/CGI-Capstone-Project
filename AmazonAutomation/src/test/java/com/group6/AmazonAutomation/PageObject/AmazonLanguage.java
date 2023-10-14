package com.group6.AmazonAutomation.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonLanguage {
    private WebDriver driver;
    private JavascriptExecutor js;

    public AmazonLanguage(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    public void navigateToHomePage(String url) {
        driver.get(url);
    }

    public void clickLanguagePreference() {
        driver.findElement(By.id("icp-nav-flyout")).click();
    }

    public void selectLanguage(String languageValue) {
        WebElement radioBtn = driver.findElement(By.xpath("//input[@type=\"radio\" and @name=\"lop\" and @value=\"" + languageValue + "\"]"));
        js.executeScript("arguments[0].click();", radioBtn);
    }

    public void saveLanguagePreference() {
        driver.findElement(By.cssSelector("#icp-save-button > span > input")).click();
    }

    public String getCurrentLanguage() {
        return driver.findElement(By.cssSelector("#icp-nav-flyout > span > span:nth-child(2) > div")).getText();
    }
}
