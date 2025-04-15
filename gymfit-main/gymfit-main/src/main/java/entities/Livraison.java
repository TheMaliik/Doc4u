package entities;

public class Livraison {
    private int id;
    private String orderId;
    private String adresseLivraison;
    private String dateLivraison;
    private String latitude;
    private String longitude; // Added longitude attribute
    private String numeroTelephone;
    private String transporteur;

    // Constructor
    public Livraison() {}

    public Livraison(int id, String orderId, String adresseLivraison, String dateLivraison,
                     String latitude, String longitude, // Updated constructor
                     String numeroTelephone, String transporteur) {
        this.id = id;
        this.orderId = orderId;
        this.adresseLivraison = adresseLivraison;
        this.dateLivraison = dateLivraison;
        this.latitude = latitude;
        this.longitude = longitude; // Initialize longitude
        this.numeroTelephone = numeroTelephone;
        this.transporteur = transporteur;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() { // Getter for longitude
        return longitude;
    }

    public void setLongitude(String longitude) { // Setter for longitude
        this.longitude = longitude;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getTransporteur() {
        return transporteur;
    }

    public void setTransporteur(String transporteur) {
        this.transporteur = transporteur;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", adresseLivraison='" + adresseLivraison + '\'' +
                ", dateLivraison='" + dateLivraison + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' + // Updated toString
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", transporteur='" + transporteur + '\'' +
                '}';
    }
}