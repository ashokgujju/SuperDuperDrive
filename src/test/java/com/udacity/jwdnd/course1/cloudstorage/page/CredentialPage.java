package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {
    @FindBy(id = "create-new-credential")
    private WebElement addNewCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/th")
    private WebElement savedCredentialUrl;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[2]")
    private WebElement savedCredentialUsername;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[3]")
    private WebElement savedCredentialPassword;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private WebElement savedCredentialEditButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
    private WebElement savedCredentialDeleteButton;

    @FindBy(xpath = "//*[@id=\"credentialModal\"]/div/div/div[3]/button[1]")
    private WebElement savedCredentialCloseDialogButton;

    protected static WebDriver driver;

    public CredentialPage(WebDriver webDriver) {
        driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void saveCredential(Credential credential) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewCredentialButton);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(credentialUrlField));

        credentialUrlField.sendKeys(credential.getUrl());
        credentialUsernameField.sendKeys(credential.getUsername());
        credentialPasswordField.sendKeys(credential.getPassword());

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialSubmitButton);
    }

    public String getSavedUrl() {
        return savedCredentialUrl.getAttribute("innerHTML");
    }

    public String getSavedUsername() {
        return savedCredentialUsername.getAttribute("innerHTML");
    }

    public String getSavedEncryptedPassword() {
        return savedCredentialPassword.getAttribute("innerHTML");
    }

    public String getViewablePassword() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(savedCredentialEditButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", savedCredentialEditButton);

        wait.until(ExpectedConditions.visibilityOf(credentialPasswordField));
        String decryptedPassword = credentialPasswordField.getAttribute("value");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", savedCredentialCloseDialogButton);

        return decryptedPassword;
    }

    public void updateCredential(String url, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(savedCredentialEditButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", savedCredentialEditButton);

        wait.until(ExpectedConditions.visibilityOf(credentialPasswordField));

        credentialUrlField.clear();
        credentialUsernameField.clear();
        credentialPasswordField.clear();

        credentialUrlField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", credentialSubmitButton);
    }

    public void deleteCredential() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", savedCredentialDeleteButton);
    }
}
