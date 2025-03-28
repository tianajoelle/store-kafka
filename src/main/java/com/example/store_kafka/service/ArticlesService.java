package com.example.store_kafka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store_kafka.model.Articles;
import com.example.store_kafka.model.Commande;
import com.example.store_kafka.repository.ArticlesRepository;
import com.example.store_kafka.repository.ClientRepository;
import com.example.store_kafka.repository.CommandeRepository;

@Service
public class ArticlesService implements ArticlesItf{
    @Autowired
    private ArticlesRepository repo;

    @Autowired
    private CommandeRepository cdeRepo;
    
    @Autowired
    private KafkaProducer kafkaProducer;
    

    public void addArticle(Long id, String nomArticle, int qte, double prix){
            Commande commande = cdeRepo.findById(id).orElseThrow(() -> new RuntimeException("Commande non trouvée"));
            Articles article = new Articles(nomArticle, qte, prix, commande);
            repo.save(article);  
    
    }

    public List<Articles> getArticlesByCommande(Long id) {
        return repo.findByCommandeId(id);
    }

    public void deleteArticle(Long idArt) {
        repo.deleteById(idArt);
    }
    
    public void validate(Long id) {
    	Optional<Commande> commandeOpt = cdeRepo.findById(id);
        
        if (commandeOpt.isPresent()) {
            // Envoi du message Kafka pour signaler la validation de la commande
            kafkaProducer.produce("Commande validée : " + id);
        } else {
            throw new RuntimeException("Commande non trouvée");
        }
    }

}
