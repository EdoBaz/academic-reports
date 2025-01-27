package demo.pages.project;

import demo.pages.HomePage;
import demo.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditProjectPage extends PageObject {

    @FindBy(name = "name")
    private WebElement nameInput;

    @FindBy(name = "description")
    private WebElement descriptionInput;

    @FindBy(name = "supervisorId")
    private WebElement supervisorSelect;

    @FindBy(name = "researcherIds")
    private WebElement researchersSelect;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(css = ".cancel-btn")
    private WebElement cancelButton;

    public EditProjectPage(WebDriver driver) {
        super(driver);
        if (!driver.getTitle().equalsIgnoreCase("Modifica Progetto")) {
            throw new IllegalStateException("Questa non è la pagina di Modifica Progetto");
        }
    }

    public EditProjectPage setName(String name) {
        this.nameInput.clear();
        this.nameInput.sendKeys(name);
        return this;
    }

    public EditProjectPage setDescription(String description) {
        this.descriptionInput.clear();
        this.descriptionInput.sendKeys(description);
        return this;
    }

    public EditProjectPage setSupervisor(String supervisorId) {
        supervisorSelect.sendKeys(supervisorId);
        return this;
    }

    public EditProjectPage setResearchers(String[] researcherIds) {
        for (String researcherId : researcherIds) {
            researchersSelect.sendKeys(researcherId);
        }
        return this;
    }

    public HomePage submitEditProject() {
        saveButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("home")); // Attendi che l'URL cambi
        return new HomePage(driver);
    }

    public EditProjectPage submitEditProjectExpectingFailure() {
        saveButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("edit")); // Verifica che l'URL rimanga su "edit"
        return this;
    }


    public String getPageTitle() {
        return driver.getTitle();
    }
}
