package ca.cal.tp2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Emprunt {
    private int borrowID;
    private LocalDate dateEmprunt;
    private String status;
    private Emprunteur emprunteur;
    private List<EmpruntDetail> empruntDetails;

    public Emprunt(int borrowID, Emprunteur emprunteur) {
        this.borrowID = borrowID;
        this.dateEmprunt = LocalDate.now();
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

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
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