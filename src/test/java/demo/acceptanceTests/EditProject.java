package demo.acceptanceTests;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.project.EditProjectPage;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EditProject extends BaseTest {

    @Test
    public void testEditProjectWorkflow() {
        // Login come amministratore
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.loginAs("admin", "admin");

        // Verifica che il ruolo non sia researcher
        String userRole = homePage.getUserRole();
        assertTrue("L'utente autenticato non è un supervisore", !userRole.equalsIgnoreCase("RESEARCHER"));

        // Test 1: Modifica progetto con successo
        EditProjectPage editProjectPage = homePage.clickEditProjectButton(0);
        homePage = editProjectPage
                .setName("Updated Project Name")
                .setDescription("Updated project description")
                .setSupervisor("bobSupervisor")
                .setResearchers(new String[]{"davidResearcher", "evaResearcher"})
                .submitEditProject();

        // Test 2: Modifica progetto (causando errore)
        editProjectPage = homePage.clickEditProjectButton(0);
        editProjectPage = editProjectPage
                .setName("") // Nome vuoto <- errore
                .setDescription("This should fail")
                .setSupervisor("bobSupervisor")
                .setResearchers(new String[]{"davidResearcher", "evaResearcher"})
                .submitEditProjectExpectingFailure();

        // Controllo che l'utente sia ancora nella pagina di modifica in quanto non tutti i parametri sono stati compilati
        String pageTitle = editProjectPage.getPageTitle();
        assertTrue("La pagina corrente non è quella di modifica del progetto",
                pageTitle.contains("Modifica Progetto"));
    }
}
