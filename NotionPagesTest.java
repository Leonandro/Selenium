import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.Duration;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotionPagesTest {
	
	private Constants resources;
	private WebDriver driver;
	private Utils loginUtil;

	@Before
	public void setUp() {
		
		resources = new Constants();
		loginUtil = new Utils();
		
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
	public void testCreatePage() throws InterruptedException {
		
		
		loginUtil.doLogin(this.driver);
		
		Thread.sleep(resources.LOGIN_WAIT_TIME);

		// Finding the Add page button by the xpath
		WebElement options = driver.findElement(By.xpath("//*[text()='Add a page']"));

		// Adding a page
		options.click();

		Thread.sleep(this.resources.LOGIN_WAIT_TIME);
	
		// Is expected that the page will be redirected to the url of the new page
		assertNotEquals(driver.getTitle(), "Getting Started");
		
		loginUtil.doLogout(this.driver);
	}
	
	@Test
	public void testSearchAPage () throws InterruptedException {
		
		loginUtil.doLogin(this.driver);
		
		Thread.sleep(resources.LOGIN_WAIT_TIME);
		
		// Finding the find button by the xpath
		WebElement options = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Quick Find']")));

		// Opening the search interface
		options.click();
		
		// Waiting until the password input appear
		WebElement searchInput = new WebDriverWait(driver, Duration.ofSeconds(4))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder=\"Search leonandro's Notion…\"]")));
		
		searchInput.sendKeys("Task List");
		
		Thread.sleep(resources.NORMAL_WAIT_TIME);
		
		List <WebElement> queryResults = driver.findElements(By.xpath("//div[@class = 'notranslate search-query-result-item notion-focusable']"));
		
		queryResults.get(0).click();
		
		Thread.sleep(resources.NORMAL_WAIT_TIME);
		
		assertEquals(driver.getTitle(), "Task List");
		
		Thread.sleep(resources.NORMAL_WAIT_TIME);
		
		loginUtil.doLogout(this.driver);
		
	}
	
	// Test for search a page that doesn't exist
	@Test
	public void testSearchAPageThatDontExist () throws InterruptedException {
		
		loginUtil.doLogin(this.driver);
		
		Thread.sleep(resources.LOGIN_WAIT_TIME);
		
		// Finding the find button by the xpath
		WebElement options = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Quick Find']")));
				

		// Opening the search interface
		options.click();
		
		// Waiting until the password input appear
		WebElement searchInput = new WebDriverWait(driver, Duration.ofSeconds(4))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder=\"Search leonandro's Notion…\"]")));
		
		searchInput.sendKeys("Page that don't exist");
		
		Thread.sleep(resources.NORMAL_WAIT_TIME);
		
		List <WebElement> queryResults = driver.findElements(By.xpath("//div[@class = 'notranslate search-query-result-item notion-focusable']"));
		
		assertEquals(queryResults.size(), 0);

	}
	
	// Test for renaming a page 
	// TODO finish this test
	@Test
	public void testRenameAPage () throws InterruptedException {
		Actions actions = new Actions(driver);

		
		loginUtil.doLogin(this.driver);
		
		Thread.sleep(resources.LOGIN_WAIT_TIME);
		
		// Finding the page button by the xpath
		WebElement page = driver.findElement(By.xpath("//*[text()='Untitled']"));

		// Opening the search interface
		actions.contextClick(page).perform();
		
		Thread.sleep(resources.NORMAL_WAIT_TIME);
		
		WebElement renameButton = driver.findElement(By.xpath("//*[text()='Rename']"));
		
		renameButton.click();
		
		
		//loginUtil.doLogout(this.driver);
		
	}
	
	//Test for deleting a page
	
	
}
