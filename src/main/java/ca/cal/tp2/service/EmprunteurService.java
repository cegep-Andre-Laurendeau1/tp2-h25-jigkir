package ca.cal.tp2.service;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.model.*;
import ca.cal.tp2.repository.InterfaceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprunteurService {

    private InterfaceRepository<Emprunteur> emprunteurRepository;
    private InterfaceRepository<Document> documentRepository;
    private InterfaceRepository<EmpruntDetails> empruntDetailsRepository;
    private InterfaceRepository<Emprunt> empruntRepository;

    public EmprunteurService(InterfaceRepository<Emprunteur> emprunteurRepository, InterfaceRepository<Document> documentRepository, InterfaceRepository<EmpruntDetails> empruntDetailsRepository, InterfaceRepository<Emprunt> empruntRepository) {
        this.emprunteurRepository = emprunteurRepository;
        this.documentRepository = documentRepository;
        this.empruntDetailsRepository = empruntDetailsRepository;
        this.empruntRepository = empruntRepository;
    }

    public Document getDocument(Long id){
        return documentRepository.get(id);
    }

    public void saveLivre(String titre, LocalDate anneePublication, int nombreExemplaire, String ISBN, String auteur, String editeur, int nombrePages) throws DatabaseException {
        try {
            documentRepository.save(new Livre(titre, anneePublication, nombreExemplaire, ISBN, auteur, editeur, nombrePages));
        }
        catch (DatabaseException e){
            System.out.println(e.getMessage());
        }
    }
    public void saveDvd(String titre, LocalDate anneePublication, int nombreExemplaire, String directeur, int duree, String genre) {
        documentRepository.save(new Dvd(titre, anneePublication, nombreExemplaire, directeur, duree, genre));
    }
    public void saveCd(String titre, LocalDate anneePublication, int nombreExemplaire, String artiste, int duree, String genre) {
        documentRepository.save(new Cd(titre, anneePublication, nombreExemplaire, artiste, duree, genre));
    }


    public void ajouterEmprunteur(String nom, String email, String numTelephone) {
        emprunteurRepository.save(new Emprunteur(nom, email, numTelephone));
    }

    public void saveExemplaire(int nmbreExemplaire, Long idDocument){
        Document document = documentRepository.get(idDocument);
        document.setNombreExemplaire(nmbreExemplaire);
        documentRepository.save(document);
    }
    public void rechercheDocument(String titreSubString, LocalDate anneePublication) {
        List<Document> documents = documentRepository.get(titreSubString, anneePublication);
        for (Document document : documents) {
            System.out.println(document.toDTO());
        }
    }
    public void rechercheDocument(String titreSubString) {
        List<Document> documents = documentRepository.get(titreSubString);
        for (Document document : documents) {
            System.out.println(document.toDTO());
        }
    }
    public void rechercheDocument(LocalDate anneePublication) {
        List<Document> documents = documentRepository.get(anneePublication);
        for (Document document : documents) {
            System.out.println(document.toDTO());
        }
    }

    public void emprunterDocument(List<Long> idDocuments, Long idEmprunteur){
        LocalDate aujourdhui = LocalDate.now();
        List<EmpruntDetails> empruntDetails = new ArrayList<>();
        Emprunt emprunt = new Emprunt(aujourdhui, "nouveau", empruntDetails, emprunteurRepository.get(idEmprunteur));

        for (int i = 0; i < idDocuments.toArray().length; i++) {
            Document documentCourant =  documentRepository.get(idDocuments.get(i));
            if(documentCourant.getNombreExemplaire() <= 0)
                throw new DatabaseException("Pas d'exemplaire disponible");
            EmpruntDetails empruntDetailsCourant = new EmpruntDetails(aujourdhui.plusWeeks(documentCourant.getDureeEmpruntSem()), "nouveau", emprunt, documentCourant);
            empruntDetails.add(empruntDetailsCourant);
        }
        empruntRepository.save(emprunt);
    }

    public void getDocumentsEmprunteur(Long idEmprunteur)throws DatabaseException{

        Emprunteur emprunteur = null;

        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunteur = emprunteurRepository.get(idEmprunteur);
            emprunts = empruntRepository.get(emprunteur);
        }
        catch (DatabaseException e){
            System.out.println(e.getMessage());
            return;
        }
        for (Emprunt emprunt : emprunts) {
            System.out.println(emprunt.toDTO());
            if(emprunt.getEmprunteur().getId() == emprunteur.getId()){
                List<EmpruntDetails> empruntDetails = empruntDetailsRepository.get(emprunt);
                for (EmpruntDetails empruntDetail : empruntDetails) {
                    System.out.println(empruntDetail.toDTO());
                    System.out.println(empruntDetail.getDocument().toDTO());
                }
            }
        }

        System.out.println("\n \n");
    }
}