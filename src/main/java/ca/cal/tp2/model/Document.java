package ca.cal.tp2.model;

public abstract class Document {
    private Long documentID;
    private String titre;
    private int nombreExemplaires;

    public Document(Long documentID, String titre, int nombreExemplaires) {
        this.documentID = documentID;
        this.titre = titre;
        this.nombreExemplaires = nombreExemplaires;
    }

    public boolean verifieDisponibilite() {
        return nombreExemplaires <= 0;
    }

    public abstract int getDureeEmprunt();

    public Long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Long documentID) {
        this.documentID = documentID;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNombreExemplaires() {
        return nombreExemplaires;
    }

    public void setNombreExemplaires(int nombreExemplaires) {
        this.nombreExemplaires = nombreExemplaires;
    }

    public void incrementExemplaires() {
        this.nombreExemplaires++;
    }

    public void decrementExemplaires() {
        if (this.nombreExemplaires > 0) {
            this.nombreExemplaires--;
        }
    }
}