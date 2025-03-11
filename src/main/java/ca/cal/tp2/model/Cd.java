package ca.cal.tp2.model;

import ca.cal.tp2.service.DTO.CdDTO;
import ca.cal.tp2.service.DTO.DocumentDTO;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@Entity
public class Cd extends Document {
    private String artiste;
    private int duree;
    private String genre;
    private final int nbJourEmprunt = 14;

    public Cd(String titre, LocalDate anneePublication, int nombreExemplaire, String artiste, int duree, String genre) {
        super(titre, anneePublication, nombreExemplaire);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }

    public Cd(Long id, String titre, LocalDate anneePublication, int nombreExemplaire, String artiste, int duree,
            String genre) {
        super(id, titre, anneePublication, nombreExemplaire);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }

    public int getDuree() { return duree; }

    public String getGenre() { return genre; }

    public int getNbJourEmprunt() { return nbJourEmprunt; }

    public String getArtiste() { return artiste; }

    @Override
    public String toString() {
        return "Cd{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", anneePublication=" + getAnneePublication() +
                ", nombreExemplaire=" + getNombreExemplaire() +
                ", artiste='" + artiste + '\'' +
                ", duree=" + duree +
                ", genre='" + genre + '\'' +
                ", nbJourEmprunt=" + nbJourEmprunt +
                '}' + "\n";
    }

    public DocumentDTO toDTO() { return new CdDTO(getTitre(), getAnneePublication(), artiste, duree, genre); }

}