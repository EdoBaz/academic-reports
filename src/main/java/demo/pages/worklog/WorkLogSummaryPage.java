package demo.pages.worklog;

import demo.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WorkLogSummaryPage extends PageObject {

    @FindBy(name = "projectId")
    private WebElement projectIdField;

    @FindBy(name = "month")
    private WebElement monthDropdown;

    @FindBy(name = "year")
    private WebElement yearDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//table[@id='summary-table']/thead/tr | //table[@id='summary-table']/tbody/tr")
    private List<WebElement> summaryTableRows;


    @FindBy(xpath = "//p[@style='color: red;']/strong")
    private WebElement errorMessage;

    private By headingLocator = By.xpath("//h2");

    public WorkLogSummaryPage(WebDriver driver) {
        super(driver);
    }

    public WorkLogSummaryPage setProject(String projectName) {
        projectIdField.sendKeys(projectName);
        return this;
    }

    public WorkLogSummaryPage setMonth(String month) {
        monthDropdown.sendKeys(month);
        return this;
    }

    public WorkLogSummaryPage setYear(String year) {
        yearDropdown.sendKeys(year);
        return this;
    }

    public WorkLogSummaryPage submitSummaryForm() {
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Prima aspetto la visibilità dell'elemento di errore, poi la presenza della riga nella tabella
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@style='color: red;']/strong")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='summary-table']/tbody/tr"))
            ));
        } catch (TimeoutException e) {
            System.out.println("Timeout reached while waiting for table rows or error message.");

        }

        return this;
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }

    public List<WebElement> getSummaryTableRows() {
        return summaryTableRows;
    }

    public String getHeading() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement headingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator));
        return headingElement.getText();
    }
}
