package com.example.Iths;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class IthsApplicationTests {

	static WebDriver driver;

	@BeforeEach
    void Setup() {
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless");
		driver = new ChromeDriver();
		driver.get("https://www.iths.se");
		driver.manage().window().maximize();
		try {
			WebElement cookieRejectButton = driver.findElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));
			cookieRejectButton.click();
		}
		catch (NoSuchElementException e) {
			System.out.println("Cookie accept button not found, continue without dismissing");
		}

	}

    @Test
    void test_title() {
		String websiteTitle = driver.getTitle();
		Assertions.assertEquals("IT-Högskolan – Här startar din IT-karriär!", websiteTitle, "Title does not match");
	}

	@Test
	void test_Welcome_to_the_spring_information_meetings_navigation() {

		WebElement spring_information = driver.findElement(By.className("banner__text"));
		spring_information.click();
		String spring_informationTitle = driver.getTitle();
		Assertions.assertEquals("Välkommen till vårens informationsträffar | IT-Högskolan", spring_informationTitle, "Title does not match");

	}

	@Test
	void test_mobile_toggle() {

		driver.manage().window().minimize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		WebElement menu = driver.findElement(By.id("mobile-toggle"));
		try {
			menu.click();
		} catch (org.openqa.selenium.ElementClickInterceptedException e) {
			((JavascriptExecutor) driver).executeScript("arguments[10].click();", menu);
		}
	}

	@Test
	void test_education_button() {

		WebElement educationbutton = driver.findElement(By.xpath("//*[text()='Alla utbildningar']"));
		educationbutton.click();

		String educationTitle = driver.getTitle();
		Assertions.assertEquals("Utbildningar | iths.se", educationTitle, "Title does not match");

	}

	@Test
	void test_education_dropdown() {

		WebElement educationbutton = driver.findElement(By.xpath("//*[text()='Alla utbildningar']"));
		educationbutton.click();

		WebElement dropdown = driver.findElement(By.id("sitesDropdown"));
		dropdown.click();

        boolean Expected_dropdown = dropdown.isDisplayed();
		Assertions.assertTrue(Expected_dropdown);

	}

	@Test
	void test_application_button() {

		WebElement apply = driver.findElement(By.linkText("Ansök här"));
		apply.click();

		String applicationTitle = driver.getTitle();
		Assertions.assertEquals("Ansökan | IT-Högskolan", applicationTitle, "Title does not match");
	}

	@Test
	void test_how_to_apply_button() {

		WebElement aboutPage_button = driver.findElement(By.id("nav-hurduansker"));
		aboutPage_button.click();


		String aboutPageTitle = driver.getTitle();
		Assertions.assertEquals("IT-Högskolan - Om skolan – Här startar din IT-karriär!", aboutPageTitle, "Title does not match");


	}

	@Test
	void play_how_to_apply_Page_video() {

		WebElement aboutPage_button = driver.findElement(By.id("nav-hurduansker"));
		aboutPage_button.click();

		WebElement videoplay_button = driver.findElement(By.className("video-link"));
		videoplay_button.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement elementAfterClick = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html//div[@id='page']//a[@href='https://www.youtube.com/embed/W5vzpwbGWJA?rel=0&autoplay=1']")));
		assert elementAfterClick.isDisplayed() : "Element did not appear after clicking the button";
		}

	@Test
	void test_news_button() {

		WebElement aboutPage_button = driver.findElement(By.id("nav-nyheter"));
		aboutPage_button.click();

		WebElement filter = driver.findElement(By.xpath("/html//section[@id='course-filter-bar']//a[@href='https://www.iths.se/category/goteborg/']"));
		filter.click();
		String filterTitle = driver.getTitle();
		Assertions.assertEquals("Göteborg-arkiv | IT-Högskolan", filterTitle, "Title does not match");
	}

	@AfterEach
	void tearDown () {
		if (driver != null) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			driver.quit();
		}
	}

	}
