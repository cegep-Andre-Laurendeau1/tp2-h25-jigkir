package ca.cal.tp2.dto;

public class LivreDTO {
    private int documentId;
    private String titre;
    private int nbExemplaires;
    private String isbn;
    private String auteur;
    private String editeur;
    private int nbPages;

    public LivreDTO(int documentId, String titre, int nbExemplaires,
                    String isbn, String auteur, String editeur, int nbPages) {
        this.documentId = documentId;
        this.titre = titre;
        this.nbExemplaires = nbExemplaires;
        this.isbn = isbn;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPages = nbPages;
    }

    // Getters
    public int getDocumentId() { return documentId; }
    public String getTitre() { return titre; }
    public int getNbExemplaires() { return nbExemplaires; }
    public String getIsbn() { return isbn; }
    public String getAuteur() { return auteur; }
    public String getEditeur() { return editeur; }
    public int getNbPages() { return nbPages; }
}
