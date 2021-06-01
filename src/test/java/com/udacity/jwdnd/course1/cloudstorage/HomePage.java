package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(css="#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(css="#nav-credentials-tab")
    private WebElement credentialsTab;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void openNotesTab() {
        this.notesTab.click();
    }

    public void openCredentialsTab() {
        this.credentialsTab.click();
    }
}
