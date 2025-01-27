package demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends PageObject {

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "surname")
    private WebElement surnameField;

    @FindBy(id = "cf")
    private WebElement cfField;

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "role")
    private WebElement roleSelect;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@class='error-message']/p")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        super(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        if (!driver.getTitle().contains("Registrazione")) {
            System.out.println("Titolo attuale della pagina: " + driver.getTitle());
            throw new IllegalStateException("Questa non è la pagina di Registrazione");
        }
    }


    public RegisterPage setName(String name) {
        nameField.sendKeys(name);
        return this;
    }

    public RegisterPage setSurname(String surname) {
        surnameField.sendKeys(surname);
        return this;
    }

    public RegisterPage setCf(String cf) {
        cfField.sendKeys(cf);
        return this;
    }

    public RegisterPage setUsername(String username) {
        usernameField.sendKeys(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public RegisterPage setRole(String role) {
        roleSelect.sendKeys(role);
        return this;
    }


    public HomePage submitForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        button.click();
        return new HomePage(driver);
    }

    public RegisterPage submitFormExpectingFailure() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        button.click();
        wait.until(ExpectedConditions.visibilityOf(errorMessage));

        return new RegisterPage(driver);
    }

    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
