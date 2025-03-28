package com.example.store_kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.store_kafka.model.Articles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper; // Pour sérialiser l'objet en JSON

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    // Méthode qui reçoit un objet Articles pour le sérialiser en message Kafka
    public void produce(Articles article) {
        try {
            // Sérialisation de l'objet Articles en JSON
            String message = objectMapper.writeValueAsString(article);
            kafkaTemplate.send("commande-topic", message);
            System.out.println("Message envoyé à Kafka : " + message); // Vérification console
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Gérer les erreurs de sérialisation ici
        }
    }
}

