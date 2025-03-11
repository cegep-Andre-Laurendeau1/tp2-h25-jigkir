package ca.cal.tp2.service.DTO;

import java.time.LocalDate;

public class LivreDTO extends DocumentDTO {

    private String ISBN;
    private String auteur;
    private String editeur;
    private int nombrePages;
    private final int nbJourEmprunt = 21;

    public LivreDTO(String titre, LocalDate anneePublication, String ISBN, String auteur, String editeur,
                    int nombrePages) {
        super(titre, anneePublication);
        this.ISBN = ISBN;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nombrePages = nombrePages;
    }

    @Override
    public String toString() {
        return "LivreDTO{" +
                "titre='" + getTitre() + '\'' +
                ", anneePublication=" + getAnneePublication() +  // <-- FIXED
                ", ISBN='" + ISBN + '\'' +
                ", auteur='" + auteur + '\'' +
                ", editeur='" + editeur + '\'' +
                ", nombrePages=" + nombrePages +
                ", nbJourEmprunt=" + nbJourEmprunt +
                '}' + "\n";
    }
}