package demo.acceptanceTests;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @Test
    public void test() {
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);

        // Login come utente
        homePage = loginPage.loginAs("supervisor", "supervisor");

        // Verifica che l'utente sia loggato correttamente
        String welcomeMessage = homePage.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Benvenuto"), "Login fallito o messaggio di benvenuto assente.");

        // Naviga alla pagina di logout
        loginPage = homePage.clickLogoutButton();

        // Verifica che l'utente sia stato disconnesso e reindirizzato alla pagina di login
        assertTrue(driver.getCurrentUrl().contains("/login"), "Logout non riuscito o URL non corretto.");

        // Esegui login con credenziali errate
        loginPage = new LoginPage(driver);
        loginPage = loginPage.loginWithIncorrectCredentials("wrongUsername", "wrongPassword");

        // Verifica che venga visualizzato il messaggio di errore
        String errorMessage = loginPage.getErrorMessage();
        assertTrue(driver.getCurrentUrl().contains("/login"), "Login non riuscito.");

        // Verifica che tentativi ad accedere a pagine senza essere autenticati rimandino alla pagina di login
        driver.get("http://localhost:8080/");
        loginPage = new LoginPage(driver);
        driver.get("http://localhost:8080/home");
        loginPage = new LoginPage(driver);
        driver.get("http://localhost:8080/create-project");
        loginPage = new LoginPage(driver);
    }

}
