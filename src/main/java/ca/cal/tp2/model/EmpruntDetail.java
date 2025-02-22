package ca.cal.tp2.model;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EmpruntDetail {
    private int lineItemID;
    private Date dateRetourPrevue;
    private Date dateRetourActuelle;
    private String status;
    private Document document;
    private Emprunt emprunt;

    public EmpruntDetail(int lineItemID, Document document, Emprunt emprunt) {
        this.lineItemID = lineItemID;
        this.document = document;
        this.emprunt = emprunt;
        this.status = "Borrowed";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, document.getDureeEmprunt());
        this.dateRetourPrevue = calendar.getTime();
    }

    public int getNombreExemplaires() {
        return document.getNombreExemplaires();
    }

    public long isEnRetard() {
        if (dateRetourActuelle != null) {
            long diff = dateRetourActuelle.getTime() - dateRetourPrevue.getTime();
            if (diff > 0) {
                return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }
            return 0;
        } else {
            long diff = new Date().getTime() - dateRetourPrevue.getTime();
            if (diff > 0) {
                return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }
            return 0;
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

    public Date getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(Date dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public Date getDateRetourActuelle() {
        return dateRetourActuelle;
    }

    public void setDateRetourActuelle(Date dateRetourActuelle) {
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