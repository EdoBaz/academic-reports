// Controller per la creazione dei progetti
package demo.controllers.project;

import demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/create-project")
public class CreateProjectController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String showCreateProjectPage(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");

        if (user == null || (user.getRole() != Role.ADMINISTRATOR && user.getRole() != Role.SUPERVISOR)) {
            return "redirect:/home";
        }

        model.addAttribute("supervisors", personRepository.findByRole(Role.SUPERVISOR));
        model.addAttribute("researchers", personRepository.findByRole(Role.RESEARCHER));

        return "create-project";
    }

    @PostMapping
    public String handleCreateProject(@RequestParam("name") String name,
                                      @RequestParam("description") String description,
                                      @RequestParam("fundingAgency") String fundingAgency,
                                      @RequestParam("month") int month,
                                      @RequestParam("year") int year,
                                      @RequestParam("supervisorId") Long supervisorId,
                                      @RequestParam("researcherIds") List<Long> researcherIds,
                                      HttpSession session,
                                      Model model) {
        Person user = (Person) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }


        Person supervisor = personRepository.findById(supervisorId).orElse(null);
        if (supervisor == null) {
            model.addAttribute("error", "Supervisor non trovato");
            model.addAttribute("supervisors", personRepository.findByRole(Role.SUPERVISOR));
            model.addAttribute("researchers", personRepository.findByRole(Role.RESEARCHER));
            return "create-project";
        }

        // Trova i ricercatori
        List<Person> researchers = (List<Person>) personRepository.findAllById(researcherIds);

        // Crea una lista vuota di WorkLog
        List<WorkLog> workLogs = new ArrayList<>();

        // Crea il nuovo progetto
        Project newProject = new Project(name, description, month, year, fundingAgency, supervisor, researchers, workLogs);

        projectRepository.save(newProject);

        return "redirect:/home";
    }
}