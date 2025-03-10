package ca.cal.tp2.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmpruntDetail {
    private int lineItemID;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourActuelle;
    private String status;
    private Document document;
    private Emprunt emprunt;

    public EmpruntDetail(int lineItemID, Document document, Emprunt emprunt) {
        this.lineItemID = lineItemID;
        this.document = document;
        this.emprunt = emprunt;
        this.status = "Borrowed";

        // Set the expected return date using LocalDate
        this.dateRetourPrevue = LocalDate.now().plusDays(document.getDureeEmprunt());
    }

    public int getNombreExemplaires() {
        return document.getNombreExemplaires();
    }

    public long isEnRetard() {
        if (dateRetourActuelle != null) {
            long diff = ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourActuelle);
            return diff > 0 ? diff : 0;
        } else {
            long diff = ChronoUnit.DAYS.between(dateRetourPrevue, LocalDate.now());
            return diff > 0 ? diff : 0;
        }
    }

    public double calculAmende() {
        long daysOverdue = isEnRetard();
        if (daysOverdue <= 0) {
            return 0.0;
        }
        return daysOverdue * 0.25;
    }

    public void updateStatus() {
        if (dateRetourActuelle != null) {
            status = "Returned";
        } else if (isEnRetard() > 0) {
            status = "Overdue";
        } else {
            status = "Borrowed";
        }
    }

    public int getLineItemID() {
        return lineItemID;
    }

    public void setLineItemID(int lineItemID) {
        this.lineItemID = lineItemID;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourActuelle() {
        return dateRetourActuelle;
    }

    public void setDateRetourActuelle(LocalDate dateRetourActuelle) {
        this.dateRetourActuelle = dateRetourActuelle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }
}