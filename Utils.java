import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {
	
	private Constants resources;
	
	public Utils () {
		resources = new Constants();
	}
	
	public void doLogin(WebDriver driver) throws InterruptedException {
		
		//Opening the login URL page
		driver.get(resources.LOGIN_URL);
				
		// Finding the input for email by the id
		WebElement emailInput = driver.findElement(By.id("notion-email-input-1"));

		// Writing the valid email
		emailInput.sendKeys(resources.EMAIL);

		// Submitting the valid email
		emailInput.sendKeys(Keys.ENTER);

		// Waiting until the password input appear
		WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(4))
				.until(ExpectedConditions.elementToBeClickable(By.id("notion-password-input-2")));

		// Finding the input for password by the id
		WebElement passwordInput = driver.findElement(By.id("notion-password-input-2"));

		// Writing the valid password
		passwordInput.sendKeys(resources.PASSWORD);

		// Submitting the valid password
		passwordInput.sendKeys(Keys.ENTER);
	}
	
	public void doLogout (WebDriver driver) throws InterruptedException {
		// Finding the account options button by the xpath
		WebElement options = driver.findElement(By.xpath("//*[text()=\"leonandro's Notion\"]"));

		// Opening the account options
		options.click();

		Thread.sleep(this.resources.CLICK_WAIT_TIME);

		// Finding the account logout button by the xpath
		WebElement logout = driver.findElement(By.xpath("//*[text()='Log out all']"));

		// Logging out
		logout.click();

		Thread.sleep(resources.LOGIN_WAIT_TIME);
	}

}
