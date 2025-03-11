package ca.cal.tp2.service.DTO;

import ca.cal.tp2.model.Emprunteur;

import java.time.LocalDate;

public record EmpruntDTO(LocalDate dateEmprunt, String status, Emprunteur emprunteur) {
    @Override
    public String toString() {
        return "EmpruntDTO{" +
                "dateEmprunt=" + dateEmprunt +
                ", status='" + status + '\'' +
                ", emprunteur=" + emprunteur +  "\n";
    }
}
