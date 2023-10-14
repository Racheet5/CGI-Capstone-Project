package StepDefinitions;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Amazonfeatures {
	static WebDriver driver;
	JavascriptExecutor js;
	
	By loginButton=By.xpath("//a[@id=\"nav-link-accountList\"]");
	By emailField=By.id("ap_email");
	By continueButton=By.id("continue");
	By passwordField=By.id("ap_password");
	By loginSubmitButton=By.id("signInSubmit");
	
	By search = By.xpath("//input[@placeholder=\"Search Amazon.in\"]");
	 By searchButton = By.xpath("//input[@id=\"nav-search-submit-button\"]");
	 By Product = By.xpath("//div[@data-index=\"4\"]");
	 By ProductName = By.xpath("//span[@id=\"productTitle\"]");
	 By addToCart = By.xpath("//input[@id=\"add-to-cart-button\"]");
	 By goToCart = By.xpath("//input[@aria-labelledby=\"attach-sidesheet-view-cart-button-announce\"]"); 
	 By AddedProduct = By.xpath("//span[@class=\"a-truncate-cut\"]");


@Given("launch the browser")
public void launch_the_browser() {
    // Write code here that turns the phrase above into concrete actions
	WebDriverManager.chromedriver().setup();
	  driver=new ChromeDriver();
	  driver.manage().window().maximize();
}

@Given("Navigate to the URL")
public void navigate_to_the_url() throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	Thread.sleep(6000);
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	  driver.get("https://www.amazon.in");
	 // Thread.sleep(3000);
}

@Given("User selects the login option")
public void user_selects_the_login_option() throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	driver.findElement(loginButton).click();
	  Thread.sleep(1000);
}

@Given("the user enters the {string}")
public void the_user_enters_the(String n) throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	driver.findElement(emailField).sendKeys(n);
	  Thread.sleep(1000);
}

@Given("User clicks continue")
public void user_clicks_continue() throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	driver.findElement(continueButton).click();
	  Thread.sleep(1000);
}

@Given("the user enters the {string} and login")
public void the_user_enters_the_and_login(String s) throws InterruptedException {
    // Write code here that turns the phrase above into concrete actions
	driver.findElement(passwordField).sendKeys(s);
	  Thread.sleep(1000);
	  driver.findElement(loginSubmitButton).click();
     Thread.sleep(3000);
	  
}

@Then("User closes the Browser")
public void user_closes_the_browser1() {
	this.driver.quit();;
}


}