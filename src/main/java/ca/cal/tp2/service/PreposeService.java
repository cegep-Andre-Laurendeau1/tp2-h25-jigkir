package ca.cal.tp2.service;

import ca.cal.tp2.model.*;
import ca.cal.tp2.repository.UtilisateurRepositoryJDBC;
import java.util.Date;
import java.util.List;

public class PreposeService {
    private final DocumentService documentService;
    private final AmendeService amendeService;
    private final EmpruntService empruntService;
    private final UtilisateurRepositoryJDBC utilisateurRepositoryJDBC;
    private static Long nextUtilisateurId = 1L;

    public PreposeService() {
        this.documentService = new DocumentService();
        this.amendeService = new AmendeService();
        this.empruntService = new EmpruntService();
        this.utilisateurRepositoryJDBC = new UtilisateurRepositoryJDBC();
    }

    public boolean entreNouveauDocument(Document document) {
        return documentService.addDocument(document);
    }

    public boolean collecteAmende(Emprunteur emprunteur, double montant) {
        return amendeService.collectAmende(emprunteur, montant);
    }

    public List<Amende> rapportAmendes() {
        return amendeService.getAllAmendes();
    }

    public List<Emprunt> rapportEmprunts() {
        return empruntService.getAllEmprunts();
    }

    public Emprunteur inscrireNouvelEmprunteur(String name, String email, String phoneNumber) {
        Emprunteur emprunteur = new Emprunteur(nextUtilisateurId++, name, email, phoneNumber);
        if (utilisateurRepositoryJDBC.save(emprunteur)) {
            return emprunteur;
        }
        return null;
    }

    public List<Emprunt> rapportEmpruntsMensuel(int month, int year) {
        return empruntService.getEmpruntsByMonth(month, year);
    }

    public double rapportAmendesTotales(Date debut, Date fin) {
        List<Amende> amendes = amendeService.getAmendesByDateRange(debut, fin);
        double total = 0.0;
        for (Amende amende : amendes) {
            total += amende.getMontant();
        }
        return total;
    }

    public List<EmpruntDetail> rapportRetourProchain(int daysAhead) {
        return empruntService.getUpcomingReturns(daysAhead);
    }

    public List<Emprunt> rapportHistoriqueEmpruntDocument(Document document) {
        return empruntService.getEmpruntsByDocument(document);
    }
}