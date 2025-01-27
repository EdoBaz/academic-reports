package demo.pages.worklog;

import demo.pages.HomePage;
import demo.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddWorkLogPage extends PageObject {

    @FindBy(xpath = "//div[@class='navigation']/a[1]/button")
    private WebElement previousMonthButton;

    @FindBy(xpath = "//div[@class='navigation']/a[2]/button")
    private WebElement nextMonthButton;

    @FindBy(xpath = "//div[@class='navigation']/h2")
    private WebElement currentMonthTitle;

    @FindBy(xpath = "//form")
    private WebElement workLogForm;

    @FindBy(xpath = "//table/tbody/tr")
    private List<WebElement> workLogRows;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-danger')]/p")
    private WebElement errorMessage;

    public AddWorkLogPage(WebDriver driver) {
        super(driver);
        // Ensure the correct page is loaded
        if (!driver.getTitle().equalsIgnoreCase("Inserimento Ore")) {
            throw new IllegalStateException("Questa non è la pagina di Inserimento Ore");
        }
    }

    public AddWorkLogPage goToPreviousMonth() {
        previousMonthButton.click();
        return new AddWorkLogPage(driver);
    }

    public AddWorkLogPage goToNextMonth() {
        nextMonthButton.click();
        return new AddWorkLogPage(driver);
    }

    public String getCurrentMonthTitle() {
        return currentMonthTitle.getText();
    }

    public void setWorkHours(String day, int hours) {
        for (WebElement row : workLogRows) {
            WebElement dateCell = row.findElement(By.xpath("./td[1]"));
            WebElement hoursInput = row.findElement(By.xpath("./td[2]/input[@type='number']"));

            if (dateCell.getText().equals(day)) {
                hoursInput.clear();
                hoursInput.sendKeys(String.valueOf(hours));
                break;
            }
        }
    }

    public HomePage saveWorkLog() {
        saveButton.click();
        return new HomePage(driver);
    }

    public AddWorkLogPage saveWorkLogExpectingFailure() {
        saveButton.click();
        return this;
    }

}
