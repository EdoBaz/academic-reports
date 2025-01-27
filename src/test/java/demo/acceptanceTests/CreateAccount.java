package demo.acceptanceTests;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.RegisterPage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CreateAccount extends BaseTest {

    @Test
    public void testCompleteAccountWorkflow() {
        // Login come amministratore
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.loginAs("admin", "admin");
        assertTrue("L'utente autenticato non è un amministratore", homePage.getUserRole().equalsIgnoreCase("ADMINISTRATOR"));

        // Creazione di un nuovo utente
        RegisterPage registerPage = homePage.clickInsertUserButton();
        String newUsername = "newuser123";
        homePage = registerPage
                .setName("Mario")
                .setSurname("Rossi")
                .setCf("RSSMRA85M01H501Z")
                .setUsername(newUsername)
                .setPassword("password")
                .setRole("USER")
                .submitForm();
        assertTrue("L'utente non è stato registrato correttamente. Messaggio di benvenuto non valido.",
                homePage.getWelcomeMessage().contains("Benvenuto"));

        // Tentativo di creare un utente con stesso username
        registerPage = homePage.clickInsertUserButton();
        registerPage
                .setName("Luigi")
                .setSurname("Verdi")
                .setCf("VRDLGU00A01L781O")
                .setUsername(newUsername) // Username duplicato
                .setPassword("Password123")
                .setRole("USER")
                .submitFormExpectingFailure();
        assertTrue("Il messaggio di errore non è corretto.",
                registerPage.getErrorMessage().contains("Il nome utente è già in uso"));
    }
}
