package demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends PageObject {

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='error-message']/p")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        if (!driver.getTitle().equalsIgnoreCase("Login")) {
            throw new IllegalStateException("Questa non è la pagina di Login");
        }
    }

    public LoginPage setUsername(String username) {
        this.username.sendKeys(username);
        return this;
    }

    public LoginPage setPassword(String password) {
        this.password.sendKeys(password);
        return this;
    }

    public HomePage submitLogin() {
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("home"));

        return new HomePage(driver);
    }

    public HomePage loginAs(String username, String password) {
        setUsername(username);
        setPassword(password);
        return submitLogin();
    }

    public LoginPage loginWithIncorrectCredentials(String username, String password) {
        setUsername(username);
        setPassword(password);
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("login"));

        return new LoginPage(driver);
    }

    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
