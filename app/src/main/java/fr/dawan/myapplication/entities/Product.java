package fr.dawan.myapplication.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //Permet de vérifier l'égalité de 2 Product
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(price, product.price) == 0 && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, price);
    }

    public static List<Product> fromJson(JSONArray jsonArray) throws Exception {
        List<Product> lst = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Product p = new Product(obj.getInt("id"), obj.getString("description"), obj.getDouble("price") );
            lst.add(p);
        }

        return lst;
    }
}
/*
Caractères d'échappement:
\n: retour à la ligne
\s: space
\b: backspace
\t: tabulation
 */