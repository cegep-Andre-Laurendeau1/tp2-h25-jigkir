package ca.cal.tp2.model;

public class CD extends Document {
    private String artiste;
    private int duree;
    private String genre;

    public CD(Long documentID, String titre, int nombreExemplaires,
              String artiste, int duree, String genre) {
        super(documentID, titre, nombreExemplaires);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }

    @Override
    public int getDureeEmprunt() {
        return 14;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}