package ca.cal.tp2.service;

import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.repository.UtilisateurRepositoryJDBC;

public class PreposeService {
    private final DocumentService documentService;
    private final AmendeService amendeService;
    private final UtilisateurRepositoryJDBC UtilisateurRepositoryJDBC;

    public PreposeService() {
        this.documentService = new DocumentService();
        this.amendeService = new AmendeService();
        this.UtilisateurRepositoryJDBC = new UtilisateurRepositoryJDBC();
    }

    public Emprunteur inscrireNouvelEmprunteur(String nom, String email, String telephone) {
        Long newUserId = System.currentTimeMillis();
        Emprunteur emprunteur = new Emprunteur(newUserId, nom, email, telephone);
        if (UtilisateurRepositoryJDBC.save(emprunteur)) {
            return emprunteur;
        }
        return null;
    }
}