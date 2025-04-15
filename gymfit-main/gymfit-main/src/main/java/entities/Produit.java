package entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Produit {
    // Valid categories
    public static final Set<String> VALID_CATEGORIES = new HashSet<>(Arrays.asList(
            "EQUIPMENT", "ACCESSORIES", "CLOTHING", "FOOD", "OTHER"
    ));

    private int id;
    private String nom; // Updated to 'nom'
    private String description;
    private float prix; // Updated to 'prix'
    private byte[] photo; // Updated to 'photo'

    public Produit() {
    }

    public Produit(int id, String nom, String description, float prix) {
        setId(id);
        setNom(nom);
        setDescription(description);
        setPrix(prix);
    }

    public Produit(int id, String nom, String description, float prix, byte[] photo) {
        this(id, nom, description, prix);
        this.photo = photo;
    }

    public Produit(int id, String nom, String description, float prix, int stock, byte[] photos) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        this.id = id;
    }

    public String getNom() { // Updated getter
        return nom;
    }

    public void setNom(String nom) { // Updated setter
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom cannot be empty");
        }
        this.nom = nom.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description.trim();
    }

    public float getPrix() { // Updated getter
        return prix;
    }

    public void setPrix(float prix) { // Updated setter
        if (prix < 0) {
            throw new IllegalArgumentException("Prix cannot be negative");
        }
        this.prix = prix;
    }

    public byte[] getPhoto() { // Updated getter
        return photo;
    }

    public void setPhoto(byte[] photo) { // Updated setter
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}