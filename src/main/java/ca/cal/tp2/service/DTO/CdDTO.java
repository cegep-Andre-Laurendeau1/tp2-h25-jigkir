package ca.cal.tp2.service.DTO;

import java.time.LocalDate;

public class CdDTO extends DocumentDTO {
    private String artiste;
    private int duree;
    private String genre;
    private final int nbJourEmprunt = 14;

    public CdDTO (String titre, LocalDate anneePublication,  String artiste, int duree, String genre) {
        super(titre, anneePublication);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "CdDTO{" +
                "titre='" + getTitre() + '\'' +
                ", anneePublication=" + getAnneePublication() +
                ", artiste='" + artiste + '\'' +
                ", duree=" + duree +
                ", genre='" + genre + '\'' +
                ", nbJourEmprunt=" + nbJourEmprunt +
                '}' + "\n";
    }
}