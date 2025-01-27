package demo.controllers;

import demo.model.Person;
import demo.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session, Model model) {
        Person user = personRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/home";
        }
        model.addAttribute("error", "Credenziali non valide. Riprova.");
        return "login";
    }


}
