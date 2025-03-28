package com.example.store_kafka.service;

import java.util.List;

import com.example.store_kafka.model.Articles;

public interface ArticlesItf {
    void addArticle(Long id, String nomArticle, int qte, double prix);

    List<Articles> getArticlesByCommande(Long id);
    
    void deleteArticle(Long idArt);

}
