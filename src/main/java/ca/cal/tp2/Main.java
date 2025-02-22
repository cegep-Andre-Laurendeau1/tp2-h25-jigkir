package ca.cal.tp2;

import ca.cal.tp2.model.Amende;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.model.Livre;
import ca.cal.tp2.service.AmendeService;
import ca.cal.tp2.service.DocumentService;
import ca.cal.tp2.service.EmpruntService;
import ca.cal.tp2.service.PreposeService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        TcpServer.startTcpServer();

        DocumentService documentService = new DocumentService();
        EmpruntService empruntService = new EmpruntService();
        AmendeService amendeService = new AmendeService();
        PreposeService preposeService = new PreposeService();

        Livre livre = new Livre(1, "Java Programming", 5, "123456789", "John Doe", "TechBooks", 500);
        documentService.addDocument(livre);

        Emprunteur emprunteur = preposeService.inscrireNouvelEmprunteur("Alice", "alice@example.com", "1234567890");

        if (empruntService.createEmprunt(emprunteur, livre)) {
            System.out.println("Document emprunté avec succès!");
        } else {
            System.out.println("Impossible d'emprunter le document.");
        }

        List<Emprunt> emprunts = empruntService.getEmpruntsForEmprunteur(emprunteur);
        System.out.println("Historique des emprunts: " + emprunts.size());

        if (empruntService.retournerDocument(emprunteur, livre)) {
            System.out.println("Document retourné avec succès!");
        } else {
            System.out.println("Échec du retour du document.");
        }

        List<Amende> amendes = amendeService.getAllAmendesByEmprunteur(emprunteur);
        System.out.println("Amendes impayées: " + amendes.size());

        Thread.currentThread().join();
    }
}
