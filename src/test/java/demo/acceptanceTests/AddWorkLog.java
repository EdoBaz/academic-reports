package demo.acceptanceTests;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.worklog.AddWorkLogPage;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddWorkLog extends BaseTest {

    @Test
    public void testAddWorkLogWorkflow() {
        // Esegui il login come ricercatore
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.loginAs("researcher", "researcher");  // Assumendo "researcher" come credenziali valide

        // Verifica che l'utente abbia accesso alla pagina di inserimento ore
        AddWorkLogPage addWorkLogPage = homePage.clickAddWorkLogButton(0); // Passando l'indice del primo progetto (0)
        addWorkLogPage = navigateToMonthYear(addWorkLogPage, "dicembre 2024");

        // Test 1: Aggiungi ore di lavoro con successo
        addWorkLogPage.setWorkHours("2024-12-24", 8);  // Impostiamo 8 ore per il 2 gennaio nel primo progetto
        HomePage homePageAfterSave = addWorkLogPage.saveWorkLog();

        // Verifica che la pagina Home sia stata caricata correttamente dopo aver salvato il worklog
        String welcomeMessage = homePageAfterSave.getWelcomeMessage();
        assertTrue("Non siamo tornati alla HomePage", welcomeMessage.contains("Benvenuto"));

        // Test 2: Tentativo di aggiungere 5 ore per lo stesso giorno ma in un progetto diverso (causando un errore)
        addWorkLogPage = homePageAfterSave.clickAddWorkLogButton(1); // Passiamo al secondo progetto
        addWorkLogPage = navigateToMonthYear(addWorkLogPage, "dicembre 2024");
        addWorkLogPage.setWorkHours("2024-12-24", 5);  // Tentiamo di aggiungere 5 ore per lo stesso giorno (2 gennaio)
        addWorkLogPage = addWorkLogPage.saveWorkLogExpectingFailure(); // Salviamo il work log

        // Controllo che la pagina non venga cambiata, ossia non sono riuscito ad inserire le ore
        String currentMonthTitle = addWorkLogPage.getCurrentMonthTitle();
        assertFalse("L'URL è cambiato, la pagina non è rimasta su add-work-log.", currentMonthTitle.isBlank());
    }

    private AddWorkLogPage navigateToMonthYear(AddWorkLogPage addWorkLogPage, String monthYear) {
        // Verifica il titolo del mese e naviga
        String currentMonthTitle = addWorkLogPage.getCurrentMonthTitle();
        if (!currentMonthTitle.contains(monthYear)) {
            // Naviga verso il mese e l'anno inserito, controllando se è necessario spostarsi avanti o indietro
            while (!currentMonthTitle.contains(monthYear)) {
                if (currentMonthTitle.compareTo(monthYear) < 0) {
                    addWorkLogPage = addWorkLogPage.goToNextMonth();
                } else {
                    addWorkLogPage = addWorkLogPage.goToPreviousMonth();
                }
                currentMonthTitle = addWorkLogPage.getCurrentMonthTitle();
            }
        }
        return addWorkLogPage;
    }

}
