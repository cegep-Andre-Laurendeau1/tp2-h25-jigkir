package ca.cal.tp2;

import ca.cal.tp2.repository.*;
import ca.cal.tp2.service.EmprunteurService;

import java.time.LocalDate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        // Votre script qui utilise votre API ici
        TcpServer.startTcpServer();

        //EmprunteurService emprunteurServiceJDBC = new EmprunteurService(new CdRepositoryJDBC(), new DvdRepositoryJDBC(), new LivreRepositoryJDBC());
        EmprunteurService emprunteurServiceJPA = new EmprunteurService(new EmprunteurRepositoryJPA(), new DocumentRepositoryJPA(), new EmpruntDetailsRepositoryJPA(), new EmpruntRepositoryJPA());



//        emprunteurServiceJDBC.saveLivre("titre JDBC", LocalDate.of(2021, 1, 1),1,"ISBN","auteur","editeur",1);
//        System.out.println(emprunteurServiceJDBC.getLivre(1L));
//
//        emprunteurServiceJDBC.saveCd("titre JDBC",LocalDate.of(2022, 1, 1),1,"artiste",1, "genre");
//        System.out.println(emprunteurServiceJDBC.getCd(1L));
//
//        emprunteurServiceJDBC.saveDvd("titre JDBC",LocalDate.of(2023, 1, 1),1,"directeur", 1, "genre");
//        System.out.println(emprunteurServiceJDBC.getDvd(1L));

        System.out.println("#####################");
        System.out.println("#Section getDocument#");
        System.out.println("#####################");

        emprunteurServiceJPA.saveLivre("titre JPA", LocalDate.of(2021, 1, 1),1,"ISBN","auteur","editeur",1);
        System.out.println("\n");

        System.out.println(emprunteurServiceJPA.getDocument(1L));
        System.out.println("\n");

        emprunteurServiceJPA.saveCd("titre JPA",LocalDate.of(2021, 1, 1),1,"artiste",1, "genre");
        System.out.println("\n");
        System.out.println(emprunteurServiceJPA.getDocument(2L));
        System.out.println("\n");

        emprunteurServiceJPA.saveDvd("titre JPA",LocalDate.of(2021, 1, 1),1,"directeur", 1, "genre");
        System.out.println("\n");
        System.out.println(emprunteurServiceJPA.getDocument(3L));
        System.out.println("\n");

        emprunteurServiceJPA.saveDvd("TITLE JPA",LocalDate.of(2021, 1, 1),1,"directeur", 1, "genre");
        System.out.println("\n");
        System.out.println(emprunteurServiceJPA.getDocument(4L));
        System.out.println("\n");

        emprunteurServiceJPA.saveDvd("title JPA",LocalDate.of(2021, 1, 1),1,"directeur", 1, "genre");
        System.out.println("\n");
        System.out.println(emprunteurServiceJPA.getDocument(5L));
        System.out.println("\n");


        System.out.println("###########################");
        System.out.println("#Section ajouterEmprunteur#");
        System.out.println("###########################");
        emprunteurServiceJPA.ajouterEmprunteur("nom", "email", "numTelephone");
        System.out.println("\n");

        System.out.println("########################");
        System.out.println("#Section saveExemplaire#");
        System.out.println("########################");
        emprunteurServiceJPA.saveExemplaire(30, 1L);
        System.out.println("\n");

        emprunteurServiceJPA.saveExemplaire(30, 2L);
        System.out.println("\n");

        System.out.println("###########################");
        System.out.println("#Section rechercheDocument#");
        System.out.println("###########################");
        emprunteurServiceJPA.rechercheDocument("titre", LocalDate.of(2021, 1, 1));
        System.out.println("\n");
        emprunteurServiceJPA.rechercheDocument("titre");
        System.out.println("\n");

        emprunteurServiceJPA.rechercheDocument("JPA");
        System.out.println("\n");

        emprunteurServiceJPA.rechercheDocument(LocalDate.of(2021, 1, 1));
        System.out.println("\n");

        System.out.println("###########################");
        System.out.println("#Section emrpunterDocument#");
        System.out.println("###########################");
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        emprunteurServiceJPA.emprunterDocument(list, 1L);
        System.out.println("\n");

        list.add(3L);
        emprunteurServiceJPA.emprunterDocument(list, 1L);
        System.out.println("\n");
        try {
            emprunteurServiceJPA.emprunterDocument(list, 1L);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("\n");

        System.out.println("################################");
        System.out.println("#Section getDocumentsEmprunteur#");
        System.out.println("################################");
        emprunteurServiceJPA.getDocumentsEmprunteur(1L);
        emprunteurServiceJPA.getDocumentsEmprunteur(400000L);

        System.out.println("\n");

        System.out.println("########################");
        System.out.println("#Section getDocumentFin#");
        System.out.println("########################");
        System.out.println(emprunteurServiceJPA.getDocument(1L));
        System.out.println("\n");
        System.out.println(emprunteurServiceJPA.getDocument(2L));
        System.out.println("\n");
        System.out.println(emprunteurServiceJPA.getDocument(3L));
        System.out.println("\n");

        Thread.currentThread().join();
    }
}