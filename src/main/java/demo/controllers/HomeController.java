package demo.controllers;

import demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/")
    public String root(HttpSession session) {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("userRole", user.getRole().name());

        List<Project> projects = new ArrayList<>();
        if (user.getRole() == Role.ADMINISTRATOR) {
            projects = (List<Project>) projectRepository.findAll();
        } else if (user.getRole() == Role.SUPERVISOR) {
            projects = projectRepository.findBySupervisorOrResearchers(user, user);
        } else if (user.getRole() == Role.RESEARCHER) {
            projects = projectRepository.findByResearchers(user);
        }

        model.addAttribute("projects", projects);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/project/delete")
    public String deleteProject(@RequestParam("id") Long projectId, HttpSession session) {
        Person user = (Person) session.getAttribute("user");

        if (user == null || user.getRole() != Role.ADMINISTRATOR) {
            return "redirect:/login";
        }

        Project project = projectRepository.findById(projectId).orElse(null);
        if (project != null) {
            projectRepository.delete(project);
        }

        return "redirect:/home";
    }


}
