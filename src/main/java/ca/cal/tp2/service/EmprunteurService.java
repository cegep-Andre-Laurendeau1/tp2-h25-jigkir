package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.model.*;
import ca.cal.tp2.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprunteurService {

    private InterfaceRepository<Emprunteur> emprunteurRepository;
    private InterfaceRepository<Document> documentRepository;
    private InterfaceRepository<EmpruntDetails> empruntDetailsRepository;
    private InterfaceRepository<Emprunt> empruntRepository;
    private InterfaceRepository<Amende> amendeRepository;

    public EmprunteurService(InterfaceRepository<Emprunteur> emprunteurRepository,
            InterfaceRepository<Document> documentRepository,
            InterfaceRepository<EmpruntDetails> empruntDetailsRepository,
            InterfaceRepository<Emprunt> empruntRepository) {
        this.emprunteurRepository = emprunteurRepository;
        this.documentRepository = documentRepository;
        this.empruntDetailsRepository = empruntDetailsRepository;
        this.empruntRepository = empruntRepository;
    }

    public EmprunteurService(CdRepositoryJDBC cdRepositoryJDBC, DvdRepositoryJDBC dvdRepositoryJDBC, LivreRepositoryJDBC livreRepositoryJDBC) {
        this.documentRepository = new DocumentRepositoryJDBC(cdRepositoryJDBC, dvdRepositoryJDBC, livreRepositoryJDBC);
    }

    public void ajouterEmprunteur(String nom, String email, String numTelephone) {
        emprunteurRepository.save(new Emprunteur(nom, email, numTelephone));
    }

    public void emprunterDocument(List<Long> idDocuments, Long idEmprunteur) {
        LocalDate aujourdhui = LocalDate.now();
        List<EmpruntDetails> empruntDetails = new ArrayList<>();
        Emprunt emprunt = new Emprunt(aujourdhui, "beingBorrowed", empruntDetails, emprunteurRepository.get(idEmprunteur));

        for (int i = 0; i < idDocuments.toArray().length; i++) {
            Document documentCourant = documentRepository.get(idDocuments.get(i));
            if (documentCourant.getNombreExemplaire() <= 0)
                throw new DatabaseException("Aucun d'exemplaire disponible" + "\n");
            String defaultStatus = "Pas d'exemplaire disponible puisque le document est emprunté";
            EmpruntDetails empruntDetailsCourant = new EmpruntDetails(
                    aujourdhui.plusWeeks(documentCourant.getNbJourEmprunt()), defaultStatus, emprunt, documentCourant);

            empruntDetails.add(empruntDetailsCourant);
        }
        empruntRepository.save(emprunt);
    }

    public Document getDocument(Long id) {
        return documentRepository.get(id);
    }

    public void saveLivre(String titre, LocalDate anneePublication, int nombreExemplaire, String ISBN, String auteur,
            String editeur, int nombrePages) throws DatabaseException {
        try {
            documentRepository
                    .save(new Livre(titre, anneePublication, nombreExemplaire, ISBN, auteur, editeur, nombrePages));
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveDvd(String titre, LocalDate anneePublication, int nombreExemplaire, String directeur, int duree,
            String genre) {
        documentRepository.save(new Dvd(titre, anneePublication, nombreExemplaire, directeur, duree, genre));
    }

    public void saveCd(String titre, LocalDate anneePublication, int nombreExemplaire, String artiste, int duree,
            String genre) {
        documentRepository.save(new Cd(titre, anneePublication, nombreExemplaire, artiste, duree, genre));
    }

    public Emprunteur getEmprunteur(Long id) {
        return emprunteurRepository.get(id);
    }

    public void saveExemplaire(int nombreExemplaire, Long idDocument) {
        Document document = documentRepository.get(idDocument);
        if (document == null) {
            System.out.println("Document with ID " + idDocument + " not found." + "\n");
            return;
        }

        document.setNombreExemplaire(nombreExemplaire);
        documentRepository.save(document);

        System.out.println("Updated Document: " + document + "\n");
    }

    public void rechercheDocument(String titreSubString, LocalDate anneePublication) {
        List<Document> documents = documentRepository.get(titreSubString, anneePublication);
        for (Document document : documents) {
            System.out.println(document);
        }
    }

    public void rechercheDocument(String titreSubString) {
        List<Document> documents = documentRepository.get(titreSubString);
        for (Document document : documents) {
            System.out.println(document);
        }
    }

    public void rechercheDocument(LocalDate anneePublication) {
        List<Document> documents = documentRepository.get(anneePublication);
        for (Document document : documents) {
            System.out.println(document);
        }
    }



    public void getDocumentsEmprunteur(Long idEmprunteur) throws DatabaseException {
        try {
            Emprunteur emprunteur = emprunteurRepository.get(idEmprunteur);
            if (emprunteur == null) {
                System.out.println("No emprunteur found with ID: " + idEmprunteur);
                return;
            }

            List<Emprunt> emprunts = empruntRepository.get(emprunteur);
            if (emprunts.isEmpty()) {
                System.out.println("No emprunts found for Emprunteur ID: " + idEmprunteur);
                return;
            }

            for (Emprunt emprunt : emprunts) {
                System.out.println(emprunt.toDTO());
                List<EmpruntDetails> empruntDetails = empruntDetailsRepository.get(emprunt);
                for (EmpruntDetails empruntDetail : empruntDetails) {
                    System.out.println(empruntDetail.toDTO().toString().replaceFirst("\\]$", "") + ", " + empruntDetail.getDocument());
                }
            }
        } catch (DatabaseException e) {
            System.out.println("Error retrieving documents for emprunteur: " + e.getMessage());
        }
    }
}
