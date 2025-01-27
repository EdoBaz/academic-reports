package demo.controllers.project;
import demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/project/edit")
public class EditProjectController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String editProject(@RequestParam("id") Long projectId, HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");

        if (user == null || (user.getRole() != Role.ADMINISTRATOR && user.getRole() != Role.SUPERVISOR)) {
            return "redirect:/login";
        }

        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null || (user.getRole() == Role.SUPERVISOR && !project.getSupervisor().equals(user))) {
            model.addAttribute("error", "Progetto non trovato o non autorizzato.");
            return "redirect:/home";
        }

        model.addAttribute("project", project);
        model.addAttribute("researchers", personRepository.findByRole(Role.RESEARCHER));
        model.addAttribute("supervisors", personRepository.findByRole(Role.SUPERVISOR));
        return "edit-project";
    }

    @PostMapping
    public String updateProject(@RequestParam("id") Long projectId,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam(value = "researcherIds", required = false) List<Long> researcherIds,
                                @RequestParam("supervisorId") Long supervisorId,
                                HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        if (user == null || (user.getRole() != Role.ADMINISTRATOR && user.getRole() != Role.SUPERVISOR)) {
            return "redirect:/login";
        }

        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null || (user.getRole() == Role.SUPERVISOR && !project.getSupervisor().equals(user))) {
            return "redirect:/home";
        }

        project.setName(name);
        project.setDescription(description);

        Person supervisor = personRepository.findById(supervisorId).orElse(null);
        if (supervisor != null) {
            project.setSupervisor(supervisor);
        }

        if (researcherIds != null) {
            List<Person> researchers = (List<Person>) personRepository.findAllById(researcherIds);
            project.setResearchers(researchers);
        }

        projectRepository.save(project);
        return "redirect:/home";
    }
}