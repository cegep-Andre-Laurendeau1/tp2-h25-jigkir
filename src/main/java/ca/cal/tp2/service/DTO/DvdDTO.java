package ca.cal.tp2.service.DTO;

import lombok.Getter;

import java.time.LocalDate;
@Getter
public class DvdDTO extends DocumentDTO{
    private String directeur;
    private int duree;
    private String genre;
    private final int dureeEmpruntSem = 1;
    public DvdDTO (String titre, LocalDate anneePublication, String directeur, int duree, String genre) {
        super(titre, anneePublication);
        this.directeur = directeur;
        this.duree = duree;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "DvdDTO{" +
                ", titre='" + getTitre() + '\'' +
                ", anneePublication=" + getAnneePublication() +
                "directeur='" + directeur + '\'' +
                ", duree=" + duree +
                ", genre='" + genre + '\'' +
                ", dureeEmpruntSem=" + dureeEmpruntSem +
                '}';
    }
}