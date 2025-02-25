package ca.cal.tp2.dto;

import java.time.LocalDate;

public class EmpruntDTO {
    private int empruntId;
    private int emprunteurId;
    private String status;
    private LocalDate dateEmprunt;

    public EmpruntDTO(int empruntId, int emprunteurId, String status, LocalDate dateEmprunt) {
        this.empruntId = empruntId;
        this.emprunteurId = emprunteurId;
        this.status = status;
        this.dateEmprunt = dateEmprunt;
    }

    // Getters
    public int getEmpruntId() { return empruntId; }
    public int getEmprunteurId() { return emprunteurId; }
    public String getStatus() { return status; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
}

