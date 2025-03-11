package ca.cal.tp2;

import ca.cal.tp2.repository.*;
import ca.cal.tp2.service.EmprunteurService;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        TcpServer.startTcpServer();

        // EmprunteurService emprunteurServiceJDBC = new EmprunteurService(new
        // CdRepositoryJDBC(), new DvdRepositoryJDBC(), new LivreRepositoryJDBC());
        // emprunteurServiceJDBC.saveLivre("titre JDBC", LocalDate.of(2021, 1,
        // 1),1,"ISBN","auteur","editeur",1);
        // System.out.println(emprunteurServiceJDBC.getLivre(1L));
        //
        // emprunteurServiceJDBC.saveCd("titre JDBC",LocalDate.of(2022, 1,
        // 1),1,"artiste",1, "genre");
        // System.out.println(emprunteurServiceJDBC.getCd(1L));
        //
        // emprunteurServiceJDBC.saveDvd("titre JDBC",LocalDate.of(2023, 1,
        // 1),1,"directeur", 1, "genre");
        // System.out.println(emprunteurServiceJDBC.getDvd(1L));

        EmprunteurService emprunteurServiceJPA = new EmprunteurService(new EmprunteurRepositoryJPA(),
                new DocumentRepositoryJPA(), new EmpruntDetailsRepositoryJPA(), new EmpruntRepositoryJPA());

        System.out.println("#####################");
        System.out.println("#Section getDocument#");
        System.out.println("#####################");

        emprunteurServiceJPA.saveLivre("titre JPA", LocalDate.of(2021, 1, 1), 1, "ISBN", "auteur", "editeur", 1);
        System.out.println(emprunteurServiceJPA.getDocument(1L));

        emprunteurServiceJPA.saveCd("titre JDBC", LocalDate.of(2021, 1, 1), 1, "artiste", 1, "genre");
        System.out.println(emprunteurServiceJPA.getDocument(2L));

        emprunteurServiceJPA.saveDvd("titre Lum", LocalDate.of(2021, 1, 1), 1, "directeur", 1, "genre");
        System.out.println(emprunteurServiceJPA.getDocument(3L));

        emprunteurServiceJPA.saveDvd("TITLE Book", LocalDate.of(2021, 1, 1), 1, "directeur", 1, "genre");
        System.out.println(emprunteurServiceJPA.getDocument(4L));

        emprunteurServiceJPA.saveDvd("title PC", LocalDate.of(2021, 1, 1), 1, "directeur", 1, "genre");
        System.out.println(emprunteurServiceJPA.getDocument(5L));

        System.out.println("###########################");
        System.out.println("#Section ajouterEmprunteur#");
        System.out.println("###########################");
        emprunteurServiceJPA.ajouterEmprunteur("Leo", "email1@gmail.com", "514-123-4567");
        System.out.println(emprunteurServiceJPA.getEmprunteur(1L));
        emprunteurServiceJPA.ajouterEmprunteur("Kirb", "email2@gmail.com", "438-123-4567");
        System.out.println(emprunteurServiceJPA.getEmprunteur(2L));

        System.out.println("########################");
        System.out.println("#Section saveExemplaire#");
        System.out.println("########################");
        emprunteurServiceJPA.saveExemplaire(30, 1L);
        emprunteurServiceJPA.saveExemplaire(30, 2L);

        System.out.println("###########################");
        System.out.println("#Section rechercheDocument#");
        System.out.println("###########################");
        emprunteurServiceJPA.rechercheDocument("titre", LocalDate.of(2021, 1, 1));
        emprunteurServiceJPA.rechercheDocument("titre");
        emprunteurServiceJPA.rechercheDocument("JPA");
        emprunteurServiceJPA.rechercheDocument(LocalDate.of(2021, 1, 1));

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
        } catch (Exception e) {
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