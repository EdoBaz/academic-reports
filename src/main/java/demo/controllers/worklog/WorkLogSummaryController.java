package demo.controllers.worklog;

import demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/worklogs/summary")
public class WorkLogSummaryController {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String showSummaryPage(
            @RequestParam(value = "projectId", required = false) Long projectId,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            HttpSession session,
            Model model) {

        // Controllo utente
        Person user = (Person) session.getAttribute("user");
        if (user == null || user.getRole() != Role.RESEARCHER) {
            return "redirect:/login";
        }

        // Lista progetti
        List<Project> projects = projectRepository.findByResearchers(user);
        model.addAttribute("projects", projects);

        // Controllo se i parametri sono stati passati
        if (projectId == null || month == null || year == null) {
            model.addAttribute("error", "Inserisci progetto, mese e anno per visualizzare il riepilogo.");
            return "work-log-summary";
        }

        // Recupero progetto
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            model.addAttribute("error", "Progetto non trovato.");
            return "work-log-summary";
        }

        try {
            // Data odierna
            LocalDate today = LocalDate.now();

            // Data richiesta
            LocalDate requestedDate = LocalDate.of(year, month, 1);

            // Data di creazione del progetto
            LocalDate projectCreationDate = LocalDate.of(project.getYear(), project.getMonth(), 1);

            // Controllo per data al passato o al futuro
            if (requestedDate.isBefore(projectCreationDate)) {
                model.addAttribute("error", "Non ci sono ore di lavoro da visualizzare nella data inserita (precedente alla creazione del progetto).");
                return "work-log-summary";
            }

            if ((requestedDate.getMonthValue() == today.getMonthValue() + 1 && requestedDate.getYear() == today.getYear()) ||
                    (today.getMonthValue() == 12 && requestedDate.getMonthValue() == 1 && requestedDate.getYear() == today.getYear() + 1) ||
                    requestedDate.isAfter(today.plusMonths(1))) {
                model.addAttribute("error", "Non ci sono ore di lavoro da visualizzare nella data inserita (data futura).");
                return "work-log-summary";
            }

            LocalDate firstDayOfMonth = requestedDate;
            LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

            // Recupero worklogs del progetto selezionato
            List<WorkLog> workLogs = workLogRepository.findByResearcherAndProject(user, project);
            List<Day> days = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1))
                    .map(date -> {
                        boolean isHoliday = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
                        WorkLog log = workLogs.stream()
                                .filter(wl -> wl.getDate().equals(date))
                                .findFirst()
                                .orElse(null);
                        return new Day(date, log != null ? log.getHours() : 0, isHoliday);
                    })
                    .toList();

            // Calcolo ore giornaliere per altri progetti con stessa fundingAgency
            Map<LocalDate, Integer> otherHoursByDay = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1))
                    .collect(Collectors.toMap(date -> date, date -> 0));

            for (WorkLog workLog : workLogRepository.findByResearcher(user)) {
                Project otherProject = workLog.getProject();
                if (otherProject.getFundingAgency().equals(project.getFundingAgency()) && !otherProject.equals(project)) {
                    LocalDate date = workLog.getDate();
                    if (otherHoursByDay.containsKey(date)) {
                        otherHoursByDay.put(date, otherHoursByDay.get(date) + workLog.getHours());
                    }
                }
            }

            // Calcolo ore giornaliere per altri progetti con fundingAgency diversa
            Map<LocalDate, Integer> differentAgencyHoursByDay = firstDayOfMonth.datesUntil(lastDayOfMonth.plusDays(1))
                    .collect(Collectors.toMap(date -> date, date -> 0));

            for (WorkLog workLog : workLogRepository.findByResearcher(user)) {
                Project otherProject = workLog.getProject();
                if (!otherProject.getFundingAgency().equals(project.getFundingAgency()) && !otherProject.equals(project)) {
                    LocalDate date = workLog.getDate();
                    if (differentAgencyHoursByDay.containsKey(date)) {
                        differentAgencyHoursByDay.put(date, differentAgencyHoursByDay.get(date) + workLog.getHours());
                    }
                }
            }

            model.addAttribute("differentAgencyHoursByDay", differentAgencyHoursByDay);

            model.addAttribute("project", project);
            model.addAttribute("days", days);
            model.addAttribute("currentMonth", month);
            model.addAttribute("currentYear", year);
            model.addAttribute("otherHoursByDay", otherHoursByDay);


        } catch (Exception e) {
            model.addAttribute("error", "Errore durante il caricamento del riepilogo.");
        }

        List<WorkLog> workLogs = workLogRepository.findByResearcherAndProject(user, project);
        int totalHours = workLogs.stream().mapToInt(WorkLog::getHours).sum();
        model.addAttribute("totalHours", totalHours); // Aggiungi totale ore al modello

        return "work-log-summary";
    }
}
