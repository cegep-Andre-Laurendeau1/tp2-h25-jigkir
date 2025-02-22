package ca.cal.tp2.model;

import java.util.Date;

public class Amende {
    private int fineID;
    private double montant;
    private Date dateCreation;
    private boolean status;
    private Emprunteur emprunteur;
    private EmpruntDetail empruntDetail;

    public Amende() {
        this.dateCreation = new Date();
        this.status = false;
    }

    public Amende(int fineID, double montant, Emprunteur emprunteur, EmpruntDetail empruntDetail) {
        this.fineID = fineID;
        this.montant = montant;
        this.dateCreation = new Date();
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

    public int getFineID() {
        return fineID;
    }

    public void setFineID(int fineID) {
        this.fineID = fineID;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
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