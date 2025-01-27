package demo.acceptanceTests;

import demo.pages.ChangePasswordPage;
import demo.pages.HomePage;
import demo.pages.LoginPage;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//TODO logout
public class ChangePassword extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private ChangePasswordPage changePasswordPage;

    @Test
    public void testChangePasswordSuccess() {
        // Esegui login come utente
        testLoginAsUser();

        // Naviga alla pagina di cambio password
        changePasswordPage = homePage.clickChangePasswordButton();

        // Cambia password con successo
        changePasswordPage.setOldPassword("frank123")
                .setNewPassword("frank1234")
                .submitChangePassword();

        // Verifica che il messaggio di successo sia visibile
        String successMessage = changePasswordPage.getSuccessMessage();
        assertEquals("Password aggiornata con successo.", successMessage);
    }

    @Test
    public void testChangePasswordWithIncorrectOldPassword() {
        // Esegui login come utente
        testLoginAsUser();

        // Naviga alla pagina di cambio password
        changePasswordPage = homePage.clickChangePasswordButton();

        // Prova a cambiare la password con una password attuale errata
        changePasswordPage.setOldPassword("wrongPassword")
                .setNewPassword("newPassword123")
                .submitChangePassword();

        // Verifica che il messaggio di errore sia visibile
        String errorMessage = changePasswordPage.getErrorMessage();
        assertEquals("La vecchia password non è corretta.", errorMessage);
    }

    @Test
    public void testCancelChangePassword() {
        // Esegui login come utente
        testLoginAsUser();

        // Naviga alla pagina di cambio password
        changePasswordPage = homePage.clickChangePasswordButton();

        // Annulla e verifica il ritorno alla home
        HomePage returnedHomePage = changePasswordPage.cancel();
        assertTrue(driver.getCurrentUrl().contains("/home"));
    }

    private void testLoginAsUser() {
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);

        // Login come utente
        homePage = loginPage.loginAs("frankResearcher", "frank123");

        // Verifica che l'utente sia loggato correttamente
        String welcomeMessage = homePage.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Benvenuto"), "Login fallito o messaggio di benvenuto assente.");
    }

}
