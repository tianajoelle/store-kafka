package com.example.store_kafka.service;

import java.util.Optional;

import com.example.store_kafka.model.Client;

public interface ClientItf {

	void registration(String email, String password, String name, String firstName);

	Optional<Client> findById(String email);

	boolean existById(String email);

	Optional<Client> findByEmail(String email);

}