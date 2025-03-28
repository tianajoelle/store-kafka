package com.example.store_kafka.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.store_kafka.model.Client;
import com.example.store_kafka.repository.ClientRepository;

@Service
public class ClientService implements ClientItf {

	@Autowired
    private ClientRepository repo;
	

    @Override
    public void registration(String email, String password, String name, String firstName) {
    	var client  = new Client(email,password, name, firstName);
		repo.save(client);
    }

    @Override
	public Optional<Client>findById(String email) {
	
		return repo.findById(email);
		
	}


	@Override
	public boolean existById(String email) {
		return repo.existsById(email);
	}

	@Override
	public Optional<Client> findByEmail(String email) {
		
		return repo.findByEmail(email);
		
	}

}
