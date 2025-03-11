package ca.cal.tp2.model;

import ca.cal.tp2.service.DTO.LivreDTO;
import ca.cal.tp2.service.DTO.DocumentDTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Livre extends Document {
    private String ISBN;
    private String auteur;
    private String editeur;
    private int nombrePages;
    private final int nbJourEmprunt = 21;

    public Livre(String titre, LocalDate anneePublication, int nombreExemplaire, String ISBN, String auteur,
            String editeur, int nombrePages) {
        super(titre, anneePublication, nombreExemplaire);
        this.ISBN = ISBN;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nombrePages = nombrePages;
    }

    public Livre(Long id, String titre, LocalDate anneePublication, int nombreExemplaire, String ISBN, String auteur,
String editeur, int nombrePages) {
        super(id, titre, anneePublication, nombreExemplaire);
        this.ISBN = ISBN;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nombrePages = nombrePages;
    }

    public String getISBN() { return ISBN; }

    public String getAuteur() { return auteur; }

    public String getEditeur() { return editeur; }

    public int getNombrePages() { return nombrePages; }

    public int getNbJourEmprunt() { return nbJourEmprunt; }

    @Override
    public String toString() {
        return "Livre{" +
                "ID=" + getId() +
                ", Titre='" + getTitre() + '\'' +
                ", Année de publication=" + getAnneePublication() +
                ", Nombre d'exemplaires=" + getNombreExemplaire() +
                ", Auteur='" + auteur + '\'' +
                ", Éditeur='" + editeur + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", Nombre de pages=" + nombrePages +
                ", Durée de prêt=" + nbJourEmprunt + " jours" +
                '}' + "\n";
    }

    public DocumentDTO toDTO() {
        return new LivreDTO(getTitre(), getAnneePublication(), ISBN, auteur, editeur, nombrePages);
    }
}