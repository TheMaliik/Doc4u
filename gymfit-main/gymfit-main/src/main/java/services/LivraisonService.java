package services;

import Utils.MyDB;
import entities.Livraison;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService {
    private Connection conx = MyDB.getConn();

    // Method to add a new livraison
    public void ajouter(Livraison livraison) throws SQLException {
        String query = "INSERT INTO livraison (order_id, adresse_livraison, date_livraison, latitude, numero_telephone_transporteur, transporteur) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conx.prepareStatement(query)) {
            statement.setString(1, livraison.getOrderId());
            statement.setString(2, livraison.getAdresseLivraison());
            statement.setString(3, livraison.getDateLivraison());
            statement.setString(4, livraison.getLatitude());
            statement.setString(5, livraison.getNumeroTelephone()); // Update this if necessary
            statement.setString(6, livraison.getTransporteur());
            statement.executeUpdate();
        }
    }

    // Method to modify an existing livraison
    public void modifier(Livraison livraison) throws SQLException {
        String query = "UPDATE livraison SET order_id=?, adresse_livraison=?, date_livraison=?, latitude=?, numero_telephone_transporteur=?, transporteur=? WHERE id=?";
        try (PreparedStatement statement = conx.prepareStatement(query)) {
            statement.setString(1, livraison.getOrderId());
            statement.setString(2, livraison.getAdresseLivraison());
            statement.setString(3, livraison.getDateLivraison());
            statement.setString(4, livraison.getLatitude());
            statement.setString(5, livraison.getNumeroTelephone()); // Update this if necessary
            statement.setString(6, livraison.getTransporteur());
            statement.setInt(7, livraison.getId());
            statement.executeUpdate();
        }
    }

    // Method to delete a livraison by ID
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM livraison WHERE id=?";
        try (PreparedStatement statement = conx.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Method to retrieve all livraisons
    public List<Livraison> afficher() throws SQLException {
        List<Livraison> livraisons = new ArrayList<>();
        String query = "SELECT * FROM livraison";
        try (Statement statement = conx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Livraison livraison = new Livraison();
                livraison.setId(resultSet.getInt("id"));
                livraison.setOrderId(resultSet.getString("order_id"));
                livraison.setAdresseLivraison(resultSet.getString("adresse_livraison"));
                livraison.setDateLivraison(resultSet.getString("date_livraison"));
                livraison.setLatitude(resultSet.getString("latitude"));
                livraison.setNumeroTelephone(resultSet.getString("numero_telephone_transporteur")); // Update this if necessary
                livraison.setTransporteur(resultSet.getString("transporteur"));
                livraisons.add(livraison);
            }
        }
        return livraisons;
    }



    public void addDelivery(String orderId, String adresseLivraison, String latitude, String longitude) throws SQLException {
        String sql = "INSERT INTO livraison (order_id, adresse_livraison, latitude, longitude) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conx.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(orderId)); // Ensure this matches the DB type
            pstmt.setString(2, adresseLivraison);
            pstmt.setString(3, latitude);
            pstmt.setString(4, longitude);
            pstmt.executeUpdate();
        }
    }

}