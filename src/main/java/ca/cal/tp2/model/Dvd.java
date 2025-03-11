package ca.cal.tp2.model;

import ca.cal.tp2.service.DTO.DocumentDTO;
import ca.cal.tp2.service.DTO.DvdDTO;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class Dvd extends Document {
    private String directeur;
    private int duree;
    private String genre;
    private final int nbJourEmprunt = 7;

    public Dvd(Long id, String titre, LocalDate anneePublication, int nombreExemplaire, String directeur, int duree, String genre) {
        super(id, titre, anneePublication, nombreExemplaire);
        this.directeur = directeur;
        this.duree = duree;
        this.genre = genre;
    }

    public Dvd(String titre, LocalDate anneePublication, int nombreExemplaire, String directeur, int duree, String genre) {
        super(titre, anneePublication, nombreExemplaire);
        this.directeur = directeur;
        this.duree = duree;
        this.genre = genre;
    }

    public String getDirecteur() { return directeur;     }

    public int getDuree() { return duree; }

    public String getGenre() {return genre; }

    public int getNbJourEmprunt() { return nbJourEmprunt; }

    @Override
    public String toString() {
        return "DVD{" +
                "ID=" + getId() +
                ", Titre='" + getTitre() + '\'' +
                ", Année de publication=" + getAnneePublication() +
                ", Nombre d'exemplaires=" + getNombreExemplaire() +
                ", Directeur='" + directeur + '\'' +
                ", Genre='" + genre + '\'' +
                ", Durée=" + duree + " min" +
                ", Durée de prêt=" + nbJourEmprunt + " jours" +
                '}' + "\n";
    }

    public DocumentDTO toDTO() {
        return new DvdDTO(getTitre(), getAnneePublication(), directeur, duree, genre);
    }
}