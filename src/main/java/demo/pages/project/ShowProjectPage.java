package demo.pages.project;

import demo.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShowProjectPage {
    private WebDriver driver;

    public ShowProjectPage(WebDriver driver) {
        this.driver = driver;
    }

    // Restituisce l'elemento che rappresenta l'header Dettaglio Progetto
    public WebElement getProjectDetailHeader() {
        return driver.findElement(By.xpath("//h1[contains(text(), 'Dettaglio Progetto')]"));
    }

    public HomePage navigateBack() {
        driver.navigate().back();
        return new HomePage(driver);
    }
}
