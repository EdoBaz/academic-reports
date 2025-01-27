package demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChangePasswordPage extends PageObject {

    @FindBy(id = "oldPassword")
    private WebElement oldPassword;

    @FindBy(id = "newPassword")
    private WebElement newPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement updatePasswordButton;

    @FindBy(xpath = "//button[contains(@class, 'cancel-btn')]")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@class='error-message']/p")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@class='success-message']/p")
    private WebElement successMessage;

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
        if (!driver.getTitle().equalsIgnoreCase("Cambia Password")) {
            throw new IllegalStateException("Questa non è la pagina di Cambia Password");
        }
    }

    public ChangePasswordPage setOldPassword(String oldPassword) {
        this.oldPassword.sendKeys(oldPassword);
        return this;
    }

    public ChangePasswordPage setNewPassword(String newPassword) {
        this.newPassword.sendKeys(newPassword);
        return this;
    }

    public ChangePasswordPage submitChangePassword() {
        updatePasswordButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Aspetto che il messaggio di successo o errore sia visibile
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOf(successMessage),
                ExpectedConditions.visibilityOf(errorMessage)
        ));

        return this;
    }

    public String getErrorMessage() {
        try {
            return errorMessage.isDisplayed() ? errorMessage.getText() : "";
        } catch (Exception e) {
            return "Error Message non caricato";
        }
    }

    public String getSuccessMessage() {
        try {
            return successMessage.isDisplayed() ? successMessage.getText() : "";
        } catch (Exception e) {
            return "Success Message non caricato";
        }
    }

    public HomePage cancel() {
        cancelButton.click();
        return new HomePage(driver);
    }
}
