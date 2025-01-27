package demo.acceptanceTests;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.project.ShowProjectPage;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

public class ViewProject extends BaseTest {

    @Test
    public void testDeleteProjectWorkflow() {
        // Naviga alla pagina di login
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);

        // Login come amministratore
        HomePage homePage = loginPage.loginAs("admin", "admin");

        // Verifica che il ruolo sia ADMINISTRATOR
        String userRole = homePage.getUserRole();
        assertTrue("L'utente autenticato non è un amministratore", userRole.equalsIgnoreCase("ADMINISTRATOR"));

        // Verifica che ci sia almeno un progetto
        int initialProjectCount = homePage.getNumberOfProjects();
        assertTrue("Non ci sono progetti da visualizzare", initialProjectCount > 0);

        // Clicca su "Visualizza" per il primo progetto e verifica il dettaglio
        ShowProjectPage showProjectPage = homePage.clickViewProjectButton(0);
        assertTrue("La pagina di dettaglio del progetto non è stata caricata correttamente",
                showProjectPage.getProjectDetailHeader().isDisplayed());

        homePage = showProjectPage.navigateBack();

        // Elimina il progetto e verifica che il numero di progetti diminuisca
        homePage = homePage.clickDeleteProjectButton(0);
        int finalProjectCount = homePage.getNumberOfProjects();
        assertTrue("Il progetto non è stato eliminato correttamente", finalProjectCount == initialProjectCount - 1);
    }

}
