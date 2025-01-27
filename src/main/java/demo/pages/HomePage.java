package demo.pages;

import demo.pages.project.CreateProjectPage;
import demo.pages.project.EditProjectPage;
import demo.pages.project.ShowProjectPage;
import demo.pages.worklog.AddWorkLogPage;
import demo.pages.worklog.WorkLogSummaryPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends PageObject {

    @FindBy(xpath = "//h2/span")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//p/span")
    private WebElement userRole;

    @FindBy(xpath = "//a[@href='/logout']/button")
    private WebElement logoutButton;

    @FindBy(xpath = "//table/tbody/tr")
    private List<WebElement> projectRows;

    @FindBy(xpath = "//a[@href='/create-project']/button")
    private WebElement createProjectButton;

    @FindBy(xpath = "//a[@href='/register']/button")
    private WebElement insertUserButton;

    @FindBy(xpath = "//a[contains(@href, '/worklogs/add')]")
    private List<WebElement> addWorkLogButtons;

    @FindBy(xpath = "//a[contains(@href, '/project/show')]")
    private List<WebElement> viewProjectButtons;

    @FindBy(xpath = "//a[contains(@href, '/project/edit')]")
    private List<WebElement> editProjectButtons;

    @FindBy(xpath = "//a[contains(@href, '/project/delete')]")
    private List<WebElement> deleteProjectButtons;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getWelcomeMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));
        String message = welcomeMessage.getText();
        return message.split(",")[0].trim();
    }

    public String getUserRole() {
        return userRole.getText();
    }

    public LoginPage clickLogoutButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/logout']/button")));
        logoutButton.click();
        wait.until(ExpectedConditions.urlContains("login"));
        return new LoginPage(driver);
    }

    public int getNumberOfProjects() {
        return projectRows.size();
    }

    public CreateProjectPage clickCreateProjectButton() {
        createProjectButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("create-project"));
        return new CreateProjectPage(driver);
    }

    public RegisterPage clickInsertUserButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement insertUserButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/register']/button")));
        insertUserButton.click();
        wait.until(ExpectedConditions.urlContains("register"));

        return new RegisterPage(driver);
    }

    public ChangePasswordPage clickChangePasswordButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement changePasswordButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/change-password']/button")));
        changePasswordButton.click();
        wait.until(ExpectedConditions.urlContains("change-password"));
        return new ChangePasswordPage(driver);
    }

    public AddWorkLogPage clickAddWorkLogButton(int projectIndex) {
        WebElement addWorkLogButton = addWorkLogButtons.get(projectIndex);
        addWorkLogButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/worklogs/add"));
        return new AddWorkLogPage(driver);
    }

    public ShowProjectPage clickViewProjectButton(int projectIndex) {
        WebElement viewProjectButton = viewProjectButtons.get(projectIndex);
        viewProjectButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/project/show"));
        return new ShowProjectPage(driver);
    }

    public EditProjectPage clickEditProjectButton(int projectIndex) {
        WebElement editProjectButton = editProjectButtons.get(projectIndex);
        editProjectButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/project/edit"));
        return new EditProjectPage(driver);
    }

    public WorkLogSummaryPage clickSummaryButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement summaryButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/worklogs/summary')]/button")));
        summaryButton.click();

        wait.until(ExpectedConditions.urlContains("work"));
        return new WorkLogSummaryPage(driver);
    }

    public HomePage clickDeleteProjectButton(int projectIndex) {
        WebElement deleteButton = deleteProjectButtons.get(projectIndex);
        deleteButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (TimeoutException e) {
            throw new RuntimeException("Il dialog di conferma non è stato mostrato in tempo.");
        }
        wait.until(ExpectedConditions.stalenessOf(deleteButton));
        return this;
    }
}
