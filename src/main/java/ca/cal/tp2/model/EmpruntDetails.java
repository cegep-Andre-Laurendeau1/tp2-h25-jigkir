package ca.cal.tp2.model;

import ca.cal.tp2.service.DTO.EmpruntDetailDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class EmpruntDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateEmpruntActuelle;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourActuelle;
    private String status;

    @ManyToOne
    private Emprunt emprunt;

    @ManyToOne
    private Document document;

    public EmpruntDetails(LocalDate dateEmpruntActuelle, String status, Emprunt emprunt, Document document) {
        this.dateEmpruntActuelle = dateEmpruntActuelle;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourPrevue = dateEmpruntActuelle.plusDays(emprunt.getNbJourEmprunt());
        this.dateRetourActuelle = (dateRetourActuelle != null) ? dateRetourActuelle : this.dateRetourPrevue;
        this.status = mapStatus(status);
        this.emprunt = emprunt;
        this.document = document;
    }

    public EmpruntDetails(LocalDate dateEmpruntActuelle, LocalDate dateRetourPrevue, String status, Emprunt emprunt, Document document) {
        this(dateEmpruntActuelle,
                status,
                emprunt,
                document);
    }

    private String mapStatus(String status) {
        if (status == null || status.equalsIgnoreCase("nouveau")) {
            return "readyToBeBorrowed";
        } else if (status.equalsIgnoreCase("not free")) {
            return "beingBorrowed";
        } else if (status.equalsIgnoreCase("Non existent")) {
            return "Pas d'exemplaire disponible";
        }
        return status;
    }

    @Override
    public String toString() {
        return "EmpruntDetails{" +
                "id=" + id +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourActuelle=" + dateRetourActuelle +
                ", status='" + status + '\'' +
                '}'+ "\n";
    }

    public EmpruntDetailDTO toDTO() {
        return new EmpruntDetailDTO(this.dateRetourPrevue, this.dateRetourActuelle, this.status, this.emprunt, this.document);
    }
}