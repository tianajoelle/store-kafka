package com.example.store_kafka.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.store_kafka.model.Articles;
import com.example.store_kafka.model.Commande;

import com.example.store_kafka.service.ArticlesService;
import com.example.store_kafka.service.CommandeService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/store")
public class ArticlesController {

    @Autowired
    private ArticlesService articleService;

    @Autowired
    private CommandeService cdeService;



    @GetMapping("/panier")
    public ModelAndView panier(@RequestParam Long id,
    		HttpSession session) {
    	String clientEmail = (String) session.getAttribute("clientEmail");
        
		if (clientEmail == null) {
        return new ModelAndView("redirect:/store/home"); // Redirige si l'utilisateur n'est pas connecté
		}
        Optional<Commande> commandeOpt = cdeService.findByCommandeId(id);
        
        if (commandeOpt.isEmpty()) {
            return new ModelAndView("redirect:/store/commande"); // Redirection si la commande n'existe pas
        }

        Commande commande = commandeOpt.get();
        List<Articles> articles = articleService.getArticlesByCommande(id);

        var model = Map.of("commande", commande, "articles", articles);
        return new ModelAndView("panier", model);
    }


    @PostMapping("/addArticle/{id}")
    public RedirectView addArticle(
            @PathVariable Long id,
            @RequestParam String nomArticle,
            @RequestParam int qte,
            @RequestParam double prix,
            RedirectAttributes redirectAttributes) {
    		
        articleService.addArticle(id, nomArticle, qte, prix);
        redirectAttributes.addFlashAttribute("success", "Article ajouté avec succès !");
        return new RedirectView("/store/panier?id=" + id);
    }

    @PostMapping("/deleteArticle/{idArt}")
    public RedirectView deleteArticle(
            @RequestParam Long id,
            @PathVariable Long idArt,
            RedirectAttributes redirectAttributes) {
        articleService.deleteArticle(idArt);
        redirectAttributes.addFlashAttribute("success", "Article supprimé avec succès !");
        return new RedirectView("/store/panier?id=" + id);
    }
    
    @GetMapping("/panier/impression")
    public ModelAndView imprimerCommande(@RequestParam Long id,
    		HttpSession session) {
    	String clientEmail = (String) session.getAttribute("clientEmail");
        
		if (clientEmail == null) {
        return new ModelAndView("redirect:/store/home"); // Redirige si l'utilisateur n'est pas connecté
		}
        Optional<Commande> commandeOpt = cdeService.findByCommandeId(id);

        if (commandeOpt.isEmpty()) {
            return new ModelAndView("redirect:/store/commande");
        }

        Commande commande = commandeOpt.get();
        List<Articles> articles = articleService.getArticlesByCommande(id);

        double total = articles.stream().mapToDouble(a -> a.getPrix() * a.getQte()).sum();

        var model = Map.of("commande", commande, "articles", articles, "total", total);
        return new ModelAndView("impression", model);
    }
    
    @PostMapping("/validate/{id}")
    public RedirectView validateCommande(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        Optional<Commande> commandeOpt = cdeService.findByCommandeId(id);
        
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            
            // Simulation de la validation
            articleService.validate(id);
            
            // Suppression après validation
            cdeService.delete(id);
            
            redirectAttributes.addFlashAttribute("success", "Commande validée !");
        } else {
            redirectAttributes.addFlashAttribute("error", "Commande introuvable !");
        }

        return new RedirectView("/store/commande");
    }



}