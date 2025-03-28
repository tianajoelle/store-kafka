package com.example.store_kafka.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.store_kafka.model.Client;

public interface ClientRepository extends CrudRepository<Client, String> {
    Optional<Client> findByEmail(String email);
}
