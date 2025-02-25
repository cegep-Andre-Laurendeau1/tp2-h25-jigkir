package ca.cal.tp2.model;

import ca.cal.tp2.service.EmpruntService;

import java.util.ArrayList;
import java.util.List;

public class Emprunteur extends Utilisateur {
    private List<Emprunt> emprunts;
    private double amendeBalance;

    public Emprunteur(int userID, String name, String email, String phoneNumber) {
        super(userID, name, email, phoneNumber);
        this.emprunts = new ArrayList<>();
        this.amendeBalance = 0.0;
    }

    public boolean emprunte(Document document) {
        if (this.amendeBalance > 0) {
            return false;
        }

        if (document.verifieDisponibilite()) {
            return false;
        }

        EmpruntService empruntService = new EmpruntService();
        return empruntService.createEmprunt(this, document);
    }

    public boolean retourneDocument(Document document) {
        EmpruntService empruntService = new EmpruntService();
        return empruntService.retournerDocument(this, document);
    }

    public boolean payeAmende(double montant) {
        if (montant <= 0 || montant > this.amendeBalance) {
            return false;
        }

        this.amendeBalance -= montant;
        return true;
    }

    public List<Emprunt> rapportHistoriqueEmprunt() {
        EmpruntService empruntService = new EmpruntService();
        return empruntService.getEmpruntsForEmprunteur(this);
    }

    public List<Emprunt> getEmprunts() {
        return emprunts;
    }

    public void setEmprunts(List<Emprunt> emprunts) {
        this.emprunts = emprunts;
    }

    public double getAmendeBalance() {
        return amendeBalance;
    }

    public void setAmendeBalance(double amendeBalance) {
        this.amendeBalance = amendeBalance;
    }

    public void addAmende(double montant) {
        this.amendeBalance += montant;
    }
}