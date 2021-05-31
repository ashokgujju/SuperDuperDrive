package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUnauthorizedUserCanOnlyAccessLoginAndSignupPages() {
		driver.get(baseURL + "/home");
		assertNotEquals("Home", driver.getTitle());

		driver.get(baseURL + "/login");
		assertEquals("Login", driver.getTitle());

		driver.get(baseURL + "/signup");
		assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testHomePageAccessAfterLoginAndAfterLogout() {
		String username = "starlord";
		String password = "milano";

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Quill", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");
		assertEquals("Home", driver.getTitle());

		driver.get(baseURL + "/logout");

		driver.get(baseURL + "/home");
		assertNotEquals("Home", driver.getTitle());
	}
}