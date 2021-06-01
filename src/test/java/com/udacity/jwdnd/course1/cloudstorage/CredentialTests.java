package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.page.CredentialPage;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests {
    @LocalServerPort
    private int port;

    private static WebDriver driver;

    public String baseURL;

    private CredentialPage credentialPage;
    private HomePage homePage;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;

        credentialPage = new CredentialPage(driver);
        homePage = new HomePage(driver);

        String username = "starlord";
        String password = "milano";

        driver.get(baseURL + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Peter", "Quill", username, password);

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

    @AfterAll
    public void afterAll() {
        driver.get(baseURL + "/logout");

        if (driver != null) {
            driver.quit();
        }
    }

    public void openCredentialsTab() {
        driver.get(baseURL + "/home");
        homePage.openCredentialsTab();
    }

    @BeforeEach
    public void beforeEach() {
        openCredentialsTab();
    }

    @Test
    @Order(1)
    public void testCredentialCreation() {
        Credential credential = new Credential();
        credential.setUrl("https://github.com/");
        credential.setUsername("ashokgujju");
        credential.setPassword("thisismypassword");
        credentialPage.saveCredential(credential);

        openCredentialsTab();

        assertEquals(credential.getUrl(), credentialPage.getSavedUrl());
        assertEquals(credential.getUsername(), credentialPage.getSavedUsername());
        assertNotEquals(credential.getPassword(), credentialPage.getSavedEncryptedPassword());
        assertEquals(credential.getPassword(), credentialPage.getViewablePassword());
    }

    @Test
    @Order(2)
    public void testEditingCredential() {
        String previousUrl = credentialPage.getSavedUrl();
        String previousUsername = credentialPage.getSavedUsername();
        String previousPassword = credentialPage.getSavedEncryptedPassword();

        String newUrl = "https://twitter.com";
        String newUsername = "ashok";
        String newPassword = "thisisnotmypassword";

        credentialPage.updateCredential(newUrl, newUsername, newPassword);

        openCredentialsTab();

        assertNotEquals(previousUrl, credentialPage.getSavedUrl());
        assertNotEquals(previousUsername, credentialPage.getSavedUsername());
        assertNotEquals(previousPassword, credentialPage.getSavedEncryptedPassword());

        assertEquals(newUrl, credentialPage.getSavedUrl());
        assertEquals(newUsername, credentialPage.getSavedUsername());
        assertNotEquals(newPassword, credentialPage.getSavedEncryptedPassword());
    }

    @Test
    @Order(3)
    public void testDeletingCredential() {
        credentialPage.deleteCredential();

        openCredentialsTab();

        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        assertEquals(0, rows.size());
    }
}
