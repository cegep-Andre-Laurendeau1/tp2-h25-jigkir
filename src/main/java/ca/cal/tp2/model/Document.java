package ca.cal.tp2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private LocalDate anneePublication;
    private int nombreExemplaire;

    public Document(String titre, LocalDate anneePublication, int nombreExemplaire) {
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.nombreExemplaire = nombreExemplaire;
    }

    public Long getId() { return id; }

    public String getTitre() { return titre; }

    public LocalDate getAnneePublication() { return anneePublication; }

    public int getNombreExemplaire() { return nombreExemplaire; }

    public void setNombreExemplaire(int nombreExemplaire) { this.nombreExemplaire = nombreExemplaire; }

    public abstract int getNbJourEmprunt();

    @Override
    public String toString() {
        return "Document{" +
                "documentID=" + id +
                ", titre='" + titre + '\'' +
                ", anneePublication=" + anneePublication +
                ", nombreExemplaire=" + nombreExemplaire +
                '}' + "\n";
    }
}