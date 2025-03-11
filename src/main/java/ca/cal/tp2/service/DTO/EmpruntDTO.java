package ca.cal.tp2.service.DTO;

import ca.cal.tp2.model.Emprunteur;

import java.time.LocalDate;

public record EmpruntDTO(
        LocalDate dateEmprunt,
        String status,
        Emprunteur emprunteur
) {
    public EmpruntDTO {
        if (dateEmprunt == null || status == null || emprunteur == null) {
            throw new IllegalArgumentException("Les champs ne peuvent pas être nuls");
        }
    }

    @Override
    public String toString() {
        return "EmpruntDTO{" +
                "dateEmprunt=" + dateEmprunt +
                ", status='" + status + '\'' +
                ", emprunteur=" + emprunteur +
                '}';
    }
}