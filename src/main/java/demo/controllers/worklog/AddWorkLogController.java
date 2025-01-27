package demo.controllers.worklog;

import demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/worklogs/add")
public class AddWorkLogController {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String showWorkLogPage(
            @RequestParam("id") Long projectId,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            HttpSession session,
            Model model) {

        Person user = (Person) session.getAttribute("user");

        if (user == null || user.getRole() != Role.RESEARCHER) {
            return "redirect:/login";
        }

        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            model.addAttribute("error", "Progetto non trovato.");
            return "redirect:/home";
        }

        LocalDate now = LocalDate.now();
        LocalDate firstDay = LocalDate.of(year != null ? year : now.getYear(), month != null ? month : now.getMonthValue(), 1);
        LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

        List<WorkLog> workLogs = workLogRepository.findByResearcherAndProject(user, project);

        List<Day> days = firstDay.datesUntil(lastDay.plusDays(1))
                .map(date -> {
                    boolean isHoliday = date.getDayOfWeek().getValue() > 5; // Sabato o Domenica
                    WorkLog log = workLogs.stream()
                            .filter(wl -> wl.getDate().equals(date))
                            .findFirst()
                            .orElse(null);
                    return new Day(date, log != null ? log.getHours() : 0, isHoliday);
                }).toList();

        model.addAttribute("project", project);
        model.addAttribute("days", days);
        model.addAttribute("currentMonth", firstDay.getMonthValue());
        model.addAttribute("currentYear", firstDay.getYear());
        model.addAttribute("previousMonth", firstDay.minusMonths(1).getMonthValue());
        model.addAttribute("previousYear", firstDay.minusMonths(1).getYear());
        model.addAttribute("nextMonth", firstDay.plusMonths(1).getMonthValue());
        model.addAttribute("nextYear", firstDay.plusMonths(1).getYear());
        model.addAttribute("today", now);
        model.addAttribute("locale", Locale.ITALIAN);

        return "add-work-log";
    }

    @PostMapping
    public String saveWorkLogs(
            @RequestParam("projectId") Long projectId,
            @RequestParam("days") List<LocalDate> days,
            @RequestParam("hours") List<Integer> hours,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Person user = (Person) session.getAttribute("user");
        Project project = projectRepository.findById(projectId).orElse(null);

        if (user == null || user.getRole() != Role.RESEARCHER || project == null) {
            return "redirect:/login";
        }

        for (int i = 0; i < days.size(); i++) {
            LocalDate day = days.get(i);
            int hour = hours.get(i);

            List<WorkLog> dailyLogs = workLogRepository.findByResearcherAndDate(user, day);

            int totalHours = dailyLogs.stream().mapToInt(WorkLog::getHours).sum();
            int oldHours = 0;
            for (WorkLog w: dailyLogs) {
                if (w.getProject().getId().equals(projectId)) {
                    oldHours = w.getHours();
                }
            }
            totalHours -= oldHours;

            if (totalHours + hour > 8) {
                redirectAttributes.addFlashAttribute("error", "Non puoi registrare più di 8 ore in totale per giorno.");
                return "redirect:/worklogs/add?id=" + projectId;
            }

            WorkLog log = workLogRepository.findByResearcherAndProjectAndDate(user, project, day);
            if (log == null) {
                log = new WorkLog(day, hour, user, project);
            } else {
                log.setHours(hour);
            }

            workLogRepository.save(log);
        }

        return "redirect:/home";
    }

}
