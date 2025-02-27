package ca.cal.tp2.service.dto;

import java.time.LocalDate;

public class EmpruntDTO {
    private Long empruntId;
    private Long emprunteurId;
    private String status;
    private LocalDate dateEmprunt;

    public EmpruntDTO(Long empruntId, Long emprunteurId, String status, LocalDate dateEmprunt) {
        this.empruntId = empruntId;
        this.emprunteurId = emprunteurId;
        this.status = status;
        this.dateEmprunt = dateEmprunt;
    }

    public Long getEmpruntId() { return empruntId; }
    public Long getEmprunteurId() { return emprunteurId; }
    public String getStatus() { return status; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
}

