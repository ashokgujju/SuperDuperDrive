package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
class NoteTests {
    @LocalServerPort
    private int port;

    private static WebDriver driver;

    public String baseURL;

    private NotePage notePage;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;

        notePage = new NotePage(driver);

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

    public void openNotesTab() {
        driver.get(baseURL + "/home");
        HomePage homePage = new HomePage(driver);
        homePage.openNotesTab();
    }

    @BeforeEach
    public void beforeEach() {
        openNotesTab();
    }

    @Test
    @Order(1)
    public void testNoteCreationAndVerify() {
        Note note = new Note();
        note.setNoteTitle("I am Groot");
        note.setNoteDescription("You're boring.");
        notePage.createNote(note);

        openNotesTab();

        assertEquals(note.getNoteTitle(), notePage.getSavedNoteTitle());
        assertEquals(note.getNoteDescription(), notePage.getSavedNoteDescription());
    }

    @Test
    @Order(2)
    public void testEditingNoteAndVerify() {
        String previousTitle = notePage.getSavedNoteTitle();
        String previousDescription = notePage.getSavedNoteDescription();

        String newTitle = "We are Groot";
        String newDescription = "Because we are one.";

        notePage.updateNote(newTitle, newDescription);

        openNotesTab();

        assertNotEquals(previousTitle, notePage.getSavedNoteTitle());
        assertNotEquals(previousDescription, notePage.getSavedNoteDescription());

        assertEquals(newTitle, notePage.getSavedNoteTitle());
        assertEquals(newDescription, notePage.getSavedNoteDescription());
    }

    @Test
    @Order(3)
    public void testDeleteNoteAndVerify() {
        notePage.deleteNote();

        openNotesTab();

        List<WebElement> rows = driver.findElements(By.tagName("tr"));
        assertEquals(0, rows.size());
    }
}
