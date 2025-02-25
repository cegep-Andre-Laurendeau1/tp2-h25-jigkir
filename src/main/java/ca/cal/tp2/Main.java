package ca.cal.tp2;

import ca.cal.tp2.dto.EmpruntDTO;
import ca.cal.tp2.dto.EmprunteurDTO;
import ca.cal.tp2.dto.LivreDTO;
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
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        TcpServer.startTcpServer();

        DocumentService documentService = new DocumentService();
        EmpruntService empruntService = new EmpruntService();
        AmendeService amendeService = new AmendeService();
        PreposeService preposeService = new PreposeService();

        // Create a LivreDTO
        LivreDTO livreDTO = documentService.createInitialBook();

        // Convert DTO to model and add document
        Livre livre = convertToLivre(livreDTO);
        documentService.addDocument(livre);

        // Create an EmprunteurDTO
        EmprunteurDTO emprunteurDTO = new EmprunteurDTO( "Alice", "alice@example.com", "1234567890" );

        // Register new borrower using DTO
        Emprunteur emprunteur = preposeService.inscrireNouvelEmprunteur(
            emprunteurDTO.getNom(),
            emprunteurDTO.getEmail(),
            emprunteurDTO.getTelephone()
        );

        // Create loan
        if (emprunteur != null && empruntService.createEmprunt(emprunteur, livre)) {
            System.out.println("Document emprunté avec succès!");
        } else {
            System.out.println("Impossible d'emprunter le document.");
        }

        // Get loans and convert to DTOs
        List<Emprunt> emprunts = empruntService.getEmpruntsForEmprunteur(emprunteur);
        List<EmpruntDTO> empruntsDTO = emprunts.stream()
            .map(Main::convertToEmpruntDTO)
            .collect(Collectors.toList());
        System.out.println("Historique des emprunts: " + empruntsDTO.size());

        // Return document
        if (empruntService.retournerDocument(emprunteur, livre)) {
            System.out.println("Document retourné avec succès!");
        } else {
            System.out.println("Échec du retour du document.");
        }

        // Get fines
        List<Amende> amendes = amendeService.getAllAmendesByEmprunteur(emprunteur);
        System.out.println("Amendes impayées: " + amendes.size());

        Thread.currentThread().join();
    }

    private static Livre convertToLivre(LivreDTO dto) {
        return new Livre(
            dto.getDocumentId(),
            dto.getTitre(),
            dto.getNbExemplaires(),
            dto.getIsbn(),
            dto.getAuteur(),
            dto.getEditeur(),
            dto.getNbPages()
        );
    }

    private static EmpruntDTO convertToEmpruntDTO(Emprunt emprunt) {
        return new EmpruntDTO(
            emprunt.getBorrowID(),
            emprunt.getEmprunteur().getId(),
            emprunt.getStatus(),
            emprunt.getDateEmprunt()
        );
    }
}