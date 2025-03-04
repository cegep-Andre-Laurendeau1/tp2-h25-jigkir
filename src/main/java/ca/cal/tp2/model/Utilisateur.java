package ca.cal.tp2.model;

public class Utilisateur {
    private Long userID;
    private String name;
    private String email;
    private String phoneNumber;

    public Utilisateur(Long userID, String name, String email, String phoneNumber) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean login() {
        return true;
    }

    public Long getUserID() { return userID; }

    public void setUserID(Long userID) { this.userID = userID; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}