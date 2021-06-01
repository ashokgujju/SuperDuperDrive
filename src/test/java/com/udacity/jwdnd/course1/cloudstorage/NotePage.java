package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {
    @FindBy(id = "create-new-note")
    private WebElement addNewNoteButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitleField;

    @FindBy(css = "#note-description")
    private WebElement noteDescriptionField;

    @FindBy(css = "#noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/th")
    private WebElement savedNoteTitle;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[2]")
    private WebElement savedNoteDescription;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
    private WebElement savedNoteEditButton;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
    private WebElement savedNoteDeleteButton;

    protected static WebDriver driver;

    public NotePage(WebDriver webDriver) {
        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void createNote(Note note){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewNoteButton);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(noteTitleField));

        noteTitleField.sendKeys(note.getNoteTitle());
        noteDescriptionField.sendKeys(note.getNoteDescription());

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteSubmitButton);
    }

    public String getSavedNoteTitle() {
        return savedNoteTitle.getAttribute("innerHTML");
    }

    public String getSavedNoteDescription() {
        return savedNoteDescription.getAttribute("innerHTML");
    }

    public void updateNote(String title, String description) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", savedNoteEditButton);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(noteTitleField));

        noteTitleField.clear();
        noteDescriptionField.clear();

        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteSubmitButton);
    }

    public void deleteNote() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", savedNoteDeleteButton);
    }
}
