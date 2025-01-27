package demo.acceptanceTests;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.project.CreateProjectPage;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

import static org.junit.Assert.*;

public class CreateProject extends BaseTest {

    @Test
    public void testCreateProjectWorkflow() {
        // Esegui il login come supervisor
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.loginAs("supervisor", "supervisor");

        // Verifica che il ruolo sia ADMINISTRATOR oppure SUPERVISOR
        String userRole = homePage.getUserRole();
        assertFalse("L'utente autenticato non è un amministratore", userRole.equalsIgnoreCase("RESEARCHER"));

        // Conteggio dei progetti prima di inserirne uno nuovo
        int initialProjectCount = homePage.getNumberOfProjects();

        // Test 1: Creazione progetto con successo
        CreateProjectPage createProjectPage = homePage.clickCreateProjectButton();
        homePage = createProjectPage
                .setProjectName("Nuovo Progetto di Ricerca")
                .setDescription("Descrizione del progetto di ricerca.")
                .setFundingAgency("Agenzia di Finanziamento")
                .setStartMonth("Gennaio")
                .setStartYear("2025")
                .selectSupervisorById("supervisor") // Supponiamo che "supervisor" sia un ID valido
                .selectResearchersByIds(List.of("researcher")) // Assegna due ricercatori
                .submitForm();

        // Controllo se il progetto è stato inserito correttamente
        int finalProjectCount = homePage.getNumberOfProjects();
        assertEquals("Il progetto non è stato inserito correttamente", finalProjectCount, initialProjectCount + 1);

        // Test 2: Creazione progetto senza ricercatori (non deve creare il progetto in questa condizione)
        createProjectPage = homePage.clickCreateProjectButton();
        homePage = createProjectPage
                .setProjectName("Progetto Senza Utenti")
                .setDescription("Descrizione di un progetto senza ricercatori.")
                .setFundingAgency("Agenzia di Finanziamento")
                .setStartMonth("Febbraio")
                .setStartYear("2025")
                .selectResearchersByIds(List.of("researcher")) // Assegna due ricercatori
                .submitFormWrong()
                .goBack(); // Torna alla homepage

    }



}
