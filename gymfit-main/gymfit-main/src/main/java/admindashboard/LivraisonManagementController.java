package admindashboard;

import com.jfoenix.controls.*;
import entities.Livraison; // Assuming you have a Livraison entity
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import services.LivraisonService; // Assuming you have a LivraisonService
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;

public class LivraisonManagementController implements Initializable {
    @FXML private StackPane rootPane;
    @FXML private FlowPane livraisonCardsContainer;
    @FXML private JFXTextField searchField;
    @FXML private JFXDialog livraisonDialog;
    @FXML private JFXTextField idField;
    @FXML private JFXTextField orderIdField;
    @FXML private JFXTextField adresseLivraisonField;
    @FXML private JFXTextField dateLivraisonField;
    @FXML private JFXTextField latitudeField;
    @FXML private JFXTextField numeroTelephoneField;
    @FXML private JFXTextField transporteurField;
    @FXML private Label idErrorLabel;
    @FXML private Label orderIdErrorLabel;
    @FXML private Label adresseLivraisonErrorLabel;
    @FXML private Label dateLivraisonErrorLabel;
    @FXML private Label latitudeErrorLabel;
    @FXML private Label numeroTelephoneErrorLabel;
    @FXML private Label transporteurErrorLabel;

    private LivraisonService livraisonService;
    private Livraison currentLivraison;
    private boolean isEditing = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        livraisonService = new LivraisonService();

        // Set dialog container
        livraisonDialog.setDialogContainer(rootPane);

        // Initialize search field listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            refreshLivraisonList(newValue);
        });

        // Initial livraison list load
        refreshLivraisonList("");
    }

    private void refreshLivraisonList(String searchTerm) {
        livraisonCardsContainer.getChildren().clear();

        try {
            livraisonService.afficher().stream()
                    .filter(livraison -> matchesSearch(livraison, searchTerm))
                    .forEach(this::createLivraisonCard);
        } catch (SQLException e) {
            showError("Error loading livraisons: " + e.getMessage());
        }
    }

    private boolean matchesSearch(Livraison livraison, String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) return true;

        String searchLower = searchTerm.toLowerCase();
        return livraison.getAdresseLivraison().toLowerCase().contains(searchLower) ||
                livraison.getTransporteur().toLowerCase().contains(searchLower);
    }

    private void createLivraisonCard(Livraison livraison) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #262626; -fx-padding: 15; -fx-background-radius: 5;");
        card.setPrefWidth(280);

        Label adresseLabel = new Label("Adresse: " + livraison.getAdresseLivraison());
        adresseLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");

        Label transporteurLabel = new Label("Transporteur: " + livraison.getTransporteur());
        transporteurLabel.setStyle("-fx-text-fill: #FF6B00;");

        HBox actions = new HBox(10);
        JFXButton editBtn = new JFXButton("Edit");
        editBtn.setStyle("-fx-background-color: #FF6B00; -fx-text-fill: white;");
        editBtn.setOnAction(e -> handleEdit(livraison));

        JFXButton deleteBtn = new JFXButton("Delete");
        deleteBtn.setStyle("-fx-background-color: #4d4d4d; -fx-text-fill: white;");
        deleteBtn.setOnAction(e -> handleDelete(livraison));

        actions.getChildren().addAll(editBtn, deleteBtn);

        card.getChildren().addAll(adresseLabel, transporteurLabel, actions);
        livraisonCardsContainer.getChildren().add(card);
    }

    @FXML
    private void handleAddLivraison() {
        isEditing = false;
        currentLivraison = null;
        clearFields();
        clearErrors();
        livraisonDialog.show();
    }

    private void handleEdit(Livraison livraison) {
        isEditing = true;
        currentLivraison = livraison;
        populateFields(livraison);
        clearErrors();
        livraisonDialog.show();
    }

    private void handleDelete(Livraison livraison) {
        try {
            livraisonService.supprimer(livraison.getId());
            refreshLivraisonList("");
        } catch (SQLException e) {
            showError("Error deleting livraison: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        try {
            clearErrors();
            boolean hasErrors = false;

            // Validate fields
            String orderId = orderIdField.getText();
            if (orderId == null || orderId.trim().isEmpty()) {
                showError(orderIdErrorLabel, "Please enter order ID");
                hasErrors = true;
            }

            String adresseLivraison = adresseLivraisonField.getText();
            if (adresseLivraison == null || adresseLivraison.trim().isEmpty()) {
                showError(adresseLivraisonErrorLabel, "Please enter delivery address");
                hasErrors = true;
            }

            String dateLivraison = dateLivraisonField.getText();
            if (dateLivraison == null || dateLivraison.trim().isEmpty()) {
                showError(dateLivraisonErrorLabel, "Please enter delivery date");
                hasErrors = true;
            }

            String latitude = latitudeField.getText();
            if (latitude == null || latitude.trim().isEmpty()) {
                showError(latitudeErrorLabel, "Please enter latitude");
                hasErrors = true;
            }

            String numeroTelephone = numeroTelephoneField.getText();
            if (numeroTelephone == null || numeroTelephone.trim().isEmpty()) {
                showError(numeroTelephoneErrorLabel, "Please enter transporteur phone number");
                hasErrors = true;
            }

            String transporteur = transporteurField.getText();
            if (transporteur == null || transporteur.trim().isEmpty()) {
                showError(transporteurErrorLabel, "Please enter transporteur name");
                hasErrors = true;
            }

            if (hasErrors) {
                return;
            }

            Livraison livraison = new Livraison();
            livraison.setOrderId(orderId);
            livraison.setAdresseLivraison(adresseLivraison);
            livraison.setDateLivraison(dateLivraison);
            livraison.setLatitude(latitude);
            livraison.setNumeroTelephone(numeroTelephone);
            livraison.setTransporteur(transporteur);

            if (isEditing && currentLivraison != null) {
                livraison.setId(currentLivraison.getId());
                livraisonService.modifier(livraison);
            } else {
                livraisonService.ajouter(livraison);
            }

            livraisonDialog.close();
            refreshLivraisonList("");

        } catch (SQLException e) {
            showError("Database error: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        livraisonDialog.close();
    }

    private void clearFields() {
        idField.clear();
        orderIdField.clear();
        adresseLivraisonField.clear();
        dateLivraisonField.clear();
        latitudeField.clear();
        numeroTelephoneField.clear();
        transporteurField.clear();
    }

    private void populateFields(Livraison livraison) {
        idField.setText(String.valueOf(livraison.getId()));
        orderIdField.setText(livraison.getOrderId());
        adresseLivraisonField.setText(livraison.getAdresseLivraison());
        dateLivraisonField.setText(livraison.getDateLivraison());
        latitudeField.setText(livraison.getLatitude());
        numeroTelephoneField.setText(livraison.getNumeroTelephone());
        transporteurField.setText(livraison.getTransporteur());
    }

    private void clearErrors() {
        idErrorLabel.setVisible(false);
        orderIdErrorLabel.setVisible(false);
        adresseLivraisonErrorLabel.setVisible(false);
        dateLivraisonErrorLabel.setVisible(false);
        latitudeErrorLabel.setVisible(false);
        numeroTelephoneErrorLabel.setVisible(false);
        transporteurErrorLabel.setVisible(false);
    }

    private void showError(Label errorLabel, String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}