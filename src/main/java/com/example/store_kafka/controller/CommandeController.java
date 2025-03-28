package com.example.store_kafka.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.store_kafka.service.CommandeItf;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store")
public class CommandeController {
    
    @Autowired
    private CommandeItf commandeService;

    @PostMapping("/create")
    public RedirectView create(
    		@RequestParam String titre,
    		HttpSession session,
    		RedirectAttributes redirectAttributes) {
        String clientEmail = (String) session.getAttribute("clientEmail");
        if (clientEmail != null) {
            commandeService.create(titre, clientEmail);
            redirectAttributes.addFlashAttribute("success", "Commande créée avec succès !");
        }
        return new RedirectView("commande");
    }

    @GetMapping("/commande")
    public ModelAndView commande(HttpSession session) {
        String clientEmail = (String) session.getAttribute("clientEmail");
        
        if (clientEmail == null) {
            return new ModelAndView("redirect:/store/home"); // Redirige si l'utilisateur n'est pas connecté
        }
        
        var commandes = commandeService.getCommandesByClient(clientEmail);
        var model = Map.of("commandes", commandes, "clientEmail", clientEmail);
        
        return new ModelAndView("commande", model);
    }

}
