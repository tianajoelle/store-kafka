package com.example.store_kafka.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.store_kafka.model.Client;
import com.example.store_kafka.service.ClientItf;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store")
public class ClientController {
	@Autowired
	private ClientItf service;
	
	@PostMapping("/registration")
	public RedirectView registration(
		    @RequestParam String email,
		    @RequestParam String password,
		    @RequestParam String name,
		    @RequestParam String firstName,
		    RedirectAttributes redirectAttributes) {
		if (service.existById(email)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Ce compte existe déjà");
	    } else {	
	        service.registration(email, password, name, firstName);
	        redirectAttributes.addFlashAttribute("success", "Compte créé avec succès");
	    }
		return new RedirectView ("/store/home");
		
	}
	
	@PostMapping("/login")
	public ModelAndView login(@RequestParam String email,
	                          @RequestParam String password,
	                          HttpSession session,
	                          Model model) {
		Optional<Client> optionalClient = service.findByEmail(email);

		if (optionalClient.isPresent() && optionalClient.get().getPassword().equals(password)) {
            // Stocker uniquement l'email dans la session
            session.setAttribute("clientEmail", optionalClient.get().getEmail());
            
            // Ajouter l'email au modèle pour le transmettre aux autres pages
            model.addAttribute("clientEmail", optionalClient.get().getEmail());
            
           
            return new ModelAndView("redirect:/store/commande");
        }else {
            // Redirection avec message d'erreur pour la connexion échouée
    		model.addAttribute("errorMessage1", "Email ou mot de passe incorrect !");
            return new ModelAndView("/home");
        }

    }
	@PostMapping("/logout")
    public RedirectView  logout(HttpSession session) {
		session.invalidate();
        return new RedirectView ("/store/home"); 
    }
	
	@GetMapping("/home")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
}
