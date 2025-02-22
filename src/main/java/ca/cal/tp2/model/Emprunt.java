package ca.cal.tp2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emprunt {
    private int borrowID;
    private Date dateEmprunt;
    private String status;
    private Emprunteur emprunteur;
    private List<EmpruntDetail> empruntDetails;

    public Emprunt() {
        this.dateEmprunt = new Date();
        this.status = "Active";
        this.empruntDetails = new ArrayList<>();
    }

    public Emprunt(int borrowID, Emprunteur emprunteur) {
        this.borrowID = borrowID;
        this.dateEmprunt = new Date();
        this.status = "Active";
        this.emprunteur = emprunteur;
        this.empruntDetails = new ArrayList<>();
    }

    public List<EmpruntDetail> getItems() {
        return empruntDetails;
    }

    public void addEmpruntDetail(EmpruntDetail detail) {
        empruntDetails.add(detail);
    }

    public int getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(int borrowID) {
        this.borrowID = borrowID;
    }

    public Date getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(Date dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Emprunteur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Emprunteur emprunteur) {
        this.emprunteur = emprunteur;
    }

    public List<EmpruntDetail> getEmpruntDetails() {
        return empruntDetails;
    }

    public void setEmpruntDetails(List<EmpruntDetail> empruntDetails) {
        this.empruntDetails = empruntDetails;
    }
}