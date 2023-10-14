package com.group6.AmazonAutomation.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class login {
    private WebDriver driver;
    private JavascriptExecutor js;

    public login(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    public void openAmazonHomepage(String url) {
        driver.get(url);
        js.executeScript("window.scrollTo(0,0)");
    }

    public void clickSignInButton() {
        driver.findElement(By.cssSelector("#nav-signin-tooltip .nav-action-inner")).click();
    }

    public void enterUserEmail(String userEmail) {
        WebElement emailInput = driver.findElement(By.id("ap_email"));
        emailInput.sendKeys(userEmail);
    }

    public void clickContinueButton() {
        driver.findElement(By.cssSelector(".a-button-inner > #continue")).click();
    }

    public void enterPassword(String password) {
        driver.findElement(By.id("ap_password")).sendKeys(password);
    }

    public void clickSignInSubmitButton() {
        driver.findElement(By.id("signInSubmit")).click();
    }

    public String getLoggedInUserName() {
        return driver.findElement(By.id("glow-ingress-block")).findElement(By.tagName("span")).getText();
    }
}
