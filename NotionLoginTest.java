import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.time.Duration;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotionLoginTest {

	private Constants resources;
	private WebDriver driver;

	@Before
	public void setUp() {
		
		resources = new Constants();
		
		//Setting the driver using a local driver for chrome
		System.setProperty("webdriver.chrome.driver", this.resources.DRIVER_PATH);
		
		driver = new ChromeDriver();
		
		//Opening the login URL page
		driver.get(this.resources.LOGIN_URL);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void testLoginWithInvalidEmail() throws InterruptedException {

		// Finding the input for email by the id
		WebElement emailInput = driver.findElement(By.id("notion-email-input-1"));

		// Writing the invalid email
		emailInput.sendKeys("invalidEmail");

		// Submitting the invalid email
		emailInput.sendKeys(Keys.ENTER);

		Thread.sleep(this.resources.NORMAL_WAIT_TIME);

		// Is expected that the input for password won't appear
		try {
			driver.findElement(By.id("notion-password-input-2"));
			fail("A invalid email pass on the validation, this is bad!");
		} catch (NoSuchElementException e) {
		}

	}

	@Test
	public void testLoginWithCorrectEmailAndWrongPWD() throws InterruptedException {

		// Finding the input for email by the id
		WebElement emailInput = driver.findElement(By.id("notion-email-input-1"));

		// Writing the valid email
		emailInput.sendKeys(this.resources.EMAIL);

		// Submitting the valid email
		emailInput.sendKeys(Keys.ENTER);

		// Waiting until the password input appear
		WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(4))
				.until(ExpectedConditions.elementToBeClickable(By.id("notion-password-input-2")));

		// Finding the input for password by the id
		WebElement passwordInput = driver.findElement(By.id("notion-password-input-2"));

		// Writing the invalid password
		passwordInput.sendKeys("invalidPassword");

		// Submitting the invalid password
		passwordInput.sendKeys(Keys.ENTER);

		Thread.sleep(this.resources.NORMAL_WAIT_TIME);

		// Is expected that the page will not be redirected to the home page
		assertEquals(driver.getCurrentUrl(), this.resources.LOGIN_URL);
	}

	@Test
	public void testLoginWithCorrectEmailAndCorrectPWD() throws InterruptedException {

		// Finding the input for email by the id
		WebElement emailInput = driver.findElement(By.id("notion-email-input-1"));

		// Writing the valid email
		emailInput.sendKeys(this.resources.EMAIL);

		// Submitting the valid email
		emailInput.sendKeys(Keys.ENTER);

		// Waiting until the password input appear
		WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(4))
				.until(ExpectedConditions.elementToBeClickable(By.id("notion-password-input-2")));

		// Finding the input for password by the id
		WebElement passwordInput = driver.findElement(By.id("notion-password-input-2"));

		// Writing the valid password
		passwordInput.sendKeys(this.resources.PASSWORD);

		// Submitting the valid password
		passwordInput.sendKeys(Keys.ENTER);

		Thread.sleep(this.resources.LOGIN_WAIT_TIME);

		// Is expected that the page will be redirected to the home page
		assertNotEquals(driver.getCurrentUrl(), this.resources.LOGIN_URL);
	}

	@Test
	public void testLogout() throws InterruptedException {

		// Finding the input for email by the id
		WebElement emailInput = driver.findElement(By.id("notion-email-input-1"));

		// Writing the valid email
		emailInput.sendKeys(this.resources.EMAIL);

		// Submitting the valid email
		emailInput.sendKeys(Keys.ENTER);

		// Waiting until the password input appear
		WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(4))
				.until(ExpectedConditions.elementToBeClickable(By.id("notion-password-input-2")));

		// Finding the input for password by the id
		WebElement passwordInput = driver.findElement(By.id("notion-password-input-2"));

		// Writing the valid password
		passwordInput.sendKeys(this.resources.PASSWORD);

		// Submitting the valid password
		passwordInput.sendKeys(Keys.ENTER);

		Thread.sleep(this.resources.LOGIN_WAIT_TIME);

		// Finding the account options button by the xpath
		WebElement options = driver.findElement(By.xpath("//*[text()=\"leonandro's Notion\"]"));

		// Opening the account options
		options.click();

		Thread.sleep(this.resources.CLICK_WAIT_TIME);

		// Finding the account logout button by the xpath
		WebElement logout = driver.findElement(By.xpath("//*[text()='Log out all']"));

		// Logging out
		logout.click();

		Thread.sleep(this.resources.LOGIN_WAIT_TIME);
		Thread.sleep(this.resources.NORMAL_WAIT_TIME);

		// Is expected that the page will be redirected to the base url of the site
		assertNotEquals(driver.getTitle(), "Getting Started");
	}

}
