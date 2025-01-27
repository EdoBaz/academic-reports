package demo.pages.project;

import demo.pages.HomePage;
import demo.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CreateProjectPage extends PageObject {

    @FindBy(id = "name")
    private WebElement projectNameField;

    @FindBy(id = "description")
    private WebElement descriptionField;

    @FindBy(id = "fundingAgency")
    private WebElement fundingAgencyField;

    @FindBy(id = "month")
    private WebElement startMonthDropdown;

    @FindBy(id = "year")
    private WebElement startYearField;

    @FindBy(id = "supervisorId")
    private WebElement supervisorDropdown;

    @FindBy(id = "researcherIds")
    private WebElement researchersDropdown;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = ".back-button")
    private WebElement backButton;


    @FindBy(xpath = "//div[@class='error-message']/p")
    private WebElement errorMessage;

    public CreateProjectPage(WebDriver driver) {
        super(driver);
    }


    // Metodo per ottenere il messaggio di benvenuto
    public String getTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        String message = titleMessage.getText();
        return message;  // Prende solo la parte prima della virgola (Benvenuto)
    }

    public CreateProjectPage setProjectName(String name) {
        projectNameField.sendKeys(name);
        return this;
    }

    public CreateProjectPage setDescription(String description) {
        descriptionField.sendKeys(description);
        return this;
    }

    public CreateProjectPage setFundingAgency(String fundingAgency) {
        fundingAgencyField.sendKeys(fundingAgency);
        return this;
    }

    public CreateProjectPage setStartMonth(String month) {
        startMonthDropdown.sendKeys(month);
        return this;
    }

    public CreateProjectPage setStartYear(String year) {
        startYearField.sendKeys(year);
        return this;
    }

    public CreateProjectPage selectSupervisorById(String supervisorId) {
        supervisorDropdown.sendKeys(supervisorId);
        return this;
    }

    public CreateProjectPage selectResearchersByIds(List<String> researcherIds) {
        for (String researcherId : researcherIds) {
            researchersDropdown.sendKeys(researcherId);
        }
        return this;
    }

    public HomePage submitForm() {
        submitButton.click();
        return new HomePage(driver);
    }

    public CreateProjectPage submitFormWrong() {
        submitButton.click();
        return new CreateProjectPage(driver);
    }

    public HomePage goBack() {
        backButton.click();
        return new HomePage(driver);
    }
}
