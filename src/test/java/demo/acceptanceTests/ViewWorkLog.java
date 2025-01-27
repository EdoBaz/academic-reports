package demo.acceptanceTests;

import demo.pages.HomePage;
import demo.pages.LoginPage;
import demo.pages.worklog.AddWorkLogPage;
import demo.pages.worklog.WorkLogSummaryPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewWorkLog extends BaseTest {

    @Test
    public void testViewWorkLogSummary() {
        // Login come ricercatore
        driver.get("http://localhost:8080/login");
        LoginPage loginPage = new LoginPage(driver);

        HomePage homePage = loginPage.loginAs("researcher", "researcher");

        // Verifica che il ruolo sia RESEARCHER
        String userRole = homePage.getUserRole();
        assertEquals("L'utente autenticato non è un ricercatore", "RESEARCHER", userRole.toUpperCase());

        // Verifica che l'utente abbia accesso alla pagina di inserimento ore
        AddWorkLogPage addWorkLogPage = homePage.clickAddWorkLogButton(0); // Passando l'indice del primo progetto (0)
        addWorkLogPage = navigateToMonthYear(addWorkLogPage, "dicembre 2024");

        // Test 1: Aggiungi ore di lavoro con successo
        addWorkLogPage.setWorkHours("2024-12-23", 8);
        HomePage homePageAfterSave = addWorkLogPage.saveWorkLog();

        // Naviga alla pagina del riepilogo
        WorkLogSummaryPage summaryPage = homePage.clickSummaryButton();

        // Compila il form di riepilogo con una data valida
        summaryPage.setProject("Project 1: AI Research")
                .setMonth("DECEMBER")         // Mese
                .setYear("2024")             // Anno
                .submitSummaryForm();

        // Controllo che l'ora inserita sia presente nella tabella del riepilogo
        boolean entryFound = false;
        List<WebElement> rows = summaryPage.getSummaryTableRows();

        // La prima riga contiene l'intestazione con le date
        WebElement headerRow = rows.get(0);
        List<WebElement> headerCells = headerRow.findElements(By.xpath(".//th | .//td"));
        int targetColumnIndex = -1;

        // Trovo l'indice della colonna per la data specifica
        for (int i = 0; i < headerCells.size(); i++) {
            String cellText = headerCells.get(i).getText();
            if (cellText.contains("23")) { // Usa solo il giorno per trovare la colonna
                targetColumnIndex = i;
                break;
            }
        }

        // Se non trovo la colonna, fallisce immediatamente
        assertTrue("La data 2024-12-23 non è presente nell'intestazione della tabella", targetColumnIndex != -1);

        // Verifico il valore nella colonna trovata per le righe successive
        for (int i = 1; i < rows.size(); i++) { // Salta la prima riga (intestazione)
            List<WebElement> cells = rows.get(i).findElements(By.xpath(".//td"));
            if (cells.size() > targetColumnIndex) {
                String cellValue = cells.get(targetColumnIndex).getText();
                if (cellValue.equals("8")) {
                    entryFound = true;
                    break;
                }
            }
        }

        // Asserzione controllo delle ore inserite precedentemente
        assertTrue("Le ore di lavoro inserite non sono presenti nella tabella del riepilogo", entryFound);

        String headingText = summaryPage.getHeading();
        assertTrue("Il testo dell'intestazione non è corretto", headingText.contains("Progetto"));

        // Compila il form di riepilogo errato
        summaryPage.setProject("Project 1: AI Research")
                .setMonth("JANUARY")         // Mese
                .setYear("2021")             // Anno precedente alla creazione
                .submitSummaryForm();

        // Controllo se mi da errore
        String errorMessage = summaryPage.getErrorMessageText();
        assertTrue("Errore atteso non corrispondente", errorMessage.contains("Non ci sono ore di lavoro da visualizzare nella data inserita"));
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
