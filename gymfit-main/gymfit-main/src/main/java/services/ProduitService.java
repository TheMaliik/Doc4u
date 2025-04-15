package services;

import entities.Produit;
import Utils.MyDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements IService<Produit> {
    private Connection conx = MyDB.getConn();

    @Override
    public void ajouter(Produit produit) throws SQLException {
        String req = "INSERT INTO produit (nom, description, prix, photo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, produit.getNom()); // Using 'nom'
            ps.setString(2, produit.getDescription());
            ps.setFloat(3, produit.getPrix()); // Using 'prix'
            ps.setBytes(4, produit.getPhoto()); // Using 'photo'
            ps.executeUpdate();

            // Get the generated ID
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    produit.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String req = "UPDATE produit SET nom=?, description=?, prix=?, photo=? WHERE id=?";
        try (PreparedStatement ps = conx.prepareStatement(req)) {
            ps.setString(1, produit.getNom()); // Using 'nom'
            ps.setString(2, produit.getDescription());
            ps.setFloat(3, produit.getPrix()); // Using 'prix'
            ps.setBytes(4, produit.getPhoto()); // Using 'photo'
            ps.setInt(5, produit.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM produit WHERE id=?";
        try (PreparedStatement ps = conx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Produit> afficher() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM produit";
        try (Statement st = conx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Produit p = new Produit(
                        rs.getInt("id"),
                        rs.getString("nom"), // Using 'nom'
                        rs.getString("description"),
                        rs.getFloat("prix"), // Using 'prix'
                        rs.getBytes("photo") // Using 'photo'
                );
                produits.add(p);
            }
        }
        return produits;
    }

    @Override
    public Produit getOne(int id) throws SQLException {
        String req = "SELECT * FROM produit WHERE id=?";
        try (PreparedStatement ps = conx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produit(
                            rs.getInt("id"),
                            rs.getString("nom"), // Using 'nom'
                            rs.getString("description"),
                            rs.getFloat("prix"), // Using 'prix'
                            rs.getBytes("photo") // Using 'photo'
                    );
                }
            }
        }
        return null;
    }

    public Produit findById(int id) throws SQLException {
        String req = "SELECT * FROM produit WHERE id = ?";
        try (PreparedStatement ps = conx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produit(
                            rs.getInt("id"),
                            rs.getString("nom"), // Using 'nom'
                            rs.getString("description"),
                            rs.getFloat("prix"), // Using 'prix'
                            rs.getBytes("photo") // Using 'photo'
                    );
                }
            }
        }
        return null;
    }

    public List<Produit> findByStock(int stock) throws SQLException {
        // Removed stock-related method
        return new ArrayList<>(); // Return an empty list or implement as needed
    }
}