package ca.cal.tp2.model;

import ca.cal.tp2.service.AmendeService;
import ca.cal.tp2.service.DocumentService;
import ca.cal.tp2.service.EmpruntService;

import java.util.List;

public class Prepose extends Utilisateur {
    public Prepose(Long userID, String name, String email, String phoneNumber) {
        super(userID, name, email, phoneNumber);
    }

    public boolean entreNouveauDocument(Document document) {
        DocumentService documentService = new DocumentService();
        return documentService.addDocument(document);
    }

    public boolean collecteAmende(Emprunteur emprunteur, double montant) {
        AmendeService amendeService = new AmendeService();
        return amendeService.collectAmende(emprunteur, montant);
    }

    public List<Amende> rapportAmendes() {
        AmendeService amendeService = new AmendeService();
        return amendeService.getAllAmendes();
    }

    public List<Emprunt> rapportEmprunts() {
        EmpruntService empruntService = new EmpruntService();
        return empruntService.getAllEmprunts();
    }
}