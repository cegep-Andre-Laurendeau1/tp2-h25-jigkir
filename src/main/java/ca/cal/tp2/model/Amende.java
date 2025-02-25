package ca.cal.tp2.model;

import java.time.LocalDate;

public class Amende {
    private int id;
    private double montant;
    private LocalDate dateCreation;
    private boolean status;
    private Emprunteur emprunteur;
    private EmpruntDetail empruntDetail;

    public Amende() {
        this.dateCreation = LocalDate.now();
        this.status = false;
    }

    public Amende(int id, double montant, Emprunteur emprunteur, EmpruntDetail empruntDetail) {
        this.id = id;
        this.montant = montant;
        this.dateCreation = LocalDate.now();
        this.status = false;
        this.emprunteur = emprunteur;
        this.empruntDetail = empruntDetail;
    }

    public double calculMontant() {
        if (empruntDetail == null) {
            return 0.0;
        }

        long daysOverdue = empruntDetail.isEnRetard();
        if (daysOverdue <= 0) {
            return 0.0;
        }

        return daysOverdue * 0.25;
    }

    public void updateStatus() {
        this.status = true;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Emprunteur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Emprunteur emprunteur) {
        this.emprunteur = emprunteur;
    }

    public EmpruntDetail getEmpruntDetail() {
        return empruntDetail;
    }

    public void setEmpruntDetail(EmpruntDetail empruntDetail) {
        this.empruntDetail = empruntDetail;
    }
}