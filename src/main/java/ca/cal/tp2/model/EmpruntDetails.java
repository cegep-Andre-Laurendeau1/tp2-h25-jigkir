package ca.cal.tp2.model;

import ca.cal.tp2.service.DTO.EmpruntDetailDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmpruntDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourActuelle;
    private String status;

    @ManyToOne
    private Emprunt emprunt;

    @ManyToOne
    private Document document;

    public EmpruntDetails(LocalDate dateRetourPrevue, LocalDate dateRetourActuelle, String status) {
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourActuelle = dateRetourActuelle;
        this.status = status;
    }
    public EmpruntDetails(LocalDate dateRetourPrevue, String status, Emprunt emprunt, Document document) {
        this.dateRetourPrevue = dateRetourPrevue;
        this.status = status;
        this.emprunt = emprunt;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public LocalDate getDateRetourActuelle() {
        return dateRetourActuelle;
    }

    public String getStatus() {
        return status;
    }
    public String toString() {
        return "EmpruntDetails{" +
                "id=" + id +
                ", dateRetourPrevue=" + dateRetourPrevue +
                ", dateRetourActuelle=" + dateRetourActuelle +
                ", status='" + status + '\'' +
                '}';
    }

    public EmpruntDetailDTO toDTO() {
        return new EmpruntDetailDTO(this.dateRetourPrevue, this.dateRetourActuelle, this.status, this.emprunt, this.document);
    }
}