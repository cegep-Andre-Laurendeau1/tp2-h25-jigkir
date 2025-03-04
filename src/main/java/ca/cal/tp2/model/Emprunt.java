package ca.cal.tp2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Emprunt {
    private Long borrowID;
    private LocalDate dateEmprunt;
    private String status;
    private Emprunteur emprunteur;
    private List<EmpruntDetail> empruntDetails;

    public Emprunt(Long borrowID, Emprunteur emprunteur) {
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

    public Long getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(Long borrowID) {
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