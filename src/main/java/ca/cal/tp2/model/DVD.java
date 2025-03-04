package ca.cal.tp2.model;

public class DVD extends Document {
    private String director;
    private int duree;
    private String rating;

    public DVD(Long documentID, String titre, int nombreExemplaires,
               String director, int duree, String rating) {
        super(documentID, titre, nombreExemplaires);
        this.director = director;
        this.duree = duree;
        this.rating = rating;
    }

    @Override
    public int getDureeEmprunt() {
        return 7;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}