package com.example.store_kafka.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArt;
    private String nomArticle;
    private int qte;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "id")
    private Commande commande;


    public Articles(){}


    public Articles(String nomArticle, int qte, double prix, Commande commande) {
        this.nomArticle = nomArticle;
        this.qte = qte;
        this.prix = prix;
        this.commande = commande;
    }


    public Long getIdArt() {
        return idArt;
    }


    public void setIdArt(Long idArt) {
        this.idArt = idArt;
    }


    public String getNomArticle() {
        return nomArticle;
    }


    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }


    public int getQte() {
        return qte;
    }


    public void setQte(int qte) {
        this.qte = qte;
    }


    public double getPrix() {
        return prix;
    }


    public void setPrix(double prix) {
        this.prix = prix;
    }


    public Commande getCommande() {
        return commande;
    }


    public void setCommande(Commande commande) {
        this.commande = commande;
    }

        

}