package fr.dawan.myapplication.entities;

import java.io.Serializable;

//Les objets de type Product peuvent être sérialiser
public class Product implements Serializable {

    //Attributs doivent être en privés
    private int id;
    private String description;
    private double price;

    //Définir les accésseurs aux attributs privés (getter/setter)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //constructeur sans params
    public Product() {
    }

    //constructeur avec l'ensemble des params
    public Product(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    //Méthode qui permet de convertir un Product en String: elle permet de choisir les attributs à afficher
    @Override
    public String toString() {
        return description + "\n" +price ;
    }
}
/*
Caractères d'échappement:
\n: retour à la ligne
\s: space
\b: backspace
\t: tabulation
 */