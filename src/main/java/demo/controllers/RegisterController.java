package demo.controllers;

import demo.model.Person;
import demo.model.PersonRepository;
import demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping
    public String handleRegistration(@RequestParam("name") String name,
                                     @RequestParam("surname") String surname,
                                     @RequestParam("CF") String CF,
                                     @RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("role") Role role, Model model) {
        Person user = personRepository.findByUsername(username);
        if (user != null) {
            model.addAttribute("error", "Il nome utente è già in uso.");
            return "register";
        }

        Person newPerson = new Person(name, surname, CF, username, password, role);
        personRepository.save(newPerson);
        return "redirect:/home";
    }
}
