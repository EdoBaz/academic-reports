package demo.controllers.project;

import demo.model.Person;
import demo.model.Project;
import demo.model.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/project/show")
public class ShowProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping
    public String showProject(@RequestParam("id") Long projectId, HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            model.addAttribute("error", "Progetto non trovato.");
            return "redirect:/home";
        }

        model.addAttribute("project", project);
        return "project-detail";
    }
}
