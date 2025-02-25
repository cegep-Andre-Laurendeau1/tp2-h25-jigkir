package ca.cal.tp2.dto;

public class EmprunteurDTO {
    private String nom;
    private String email;
    private String telephone;

    public EmprunteurDTO(String nom, String email, String telephone) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    // Getters
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getTelephone() { return telephone; }
}

