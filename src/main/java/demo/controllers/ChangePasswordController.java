package demo.controllers;

import demo.model.Person;
import demo.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/change-password")
public class ChangePasswordController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public String showChangePasswordPage(HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "change-password";
    }

    @PostMapping
    public String handlePasswordChange(@RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       HttpSession session,
                                       Model model) {
        Person user = (Person) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        if (!user.getPassword().equals(oldPassword)) { // Verifica semplice; per maggiore sicurezza usa hashing.
            model.addAttribute("error", "La vecchia password non è corretta.");
            return "change-password";
        }

        user.setPassword(newPassword); // Aggiorna la password.
        personRepository.save(user); // Salva l'utente aggiornato.

        model.addAttribute("success", "Password aggiornata con successo.");
        return "change-password";
    }
}

