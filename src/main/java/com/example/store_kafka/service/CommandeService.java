package com.example.store_kafka.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store_kafka.model.Commande;
import com.example.store_kafka.repository.ClientRepository;
import com.example.store_kafka.repository.CommandeRepository;

@Service
public class CommandeService implements CommandeItf{
	
	@Autowired
    private CommandeRepository repo;

    @Autowired
    private ClientRepository clientRepo;
   

	@Override
	public void create(String titre, String clientEmail) {
        clientRepo.findById(clientEmail).ifPresent(client -> {
            Commande commande = new Commande(titre, client);
            repo.save(commande);
        });
	}

	@Override
	public List<Commande> getCommandesByClient(String clientEmail) {
		return repo.findByClientEmail(clientEmail);
	}

    @Override
	public Optional<Commande> findByCommandeId(Long id) {
    	return repo.findById(id); 
	}
    
    @Override
    public void delete(Long id) {
        Commande commande = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        repo.delete(commande);
    }

}
