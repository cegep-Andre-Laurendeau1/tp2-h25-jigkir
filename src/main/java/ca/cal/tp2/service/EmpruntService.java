package ca.cal.tp2.service;

import ca.cal.tp2.model.*;
import ca.cal.tp2.repository.EmpruntRepositoryJDBC;
import java.util.Date;
import java.util.List;

public class EmpruntService {
    private final EmpruntRepositoryJDBC empruntRepositoryJDBC = new EmpruntRepositoryJDBC();
    private static Long nextEmpruntId = 1L;
    private static int nextEmpruntDetailId = 1;

    public List<Emprunt> getEmpruntsByMonth(int month, int year) {
        return empruntRepositoryJDBC.findByMonth(month, year);
    }

    public List<EmpruntDetail> getUpcomingReturns(int daysAhead) {
        return empruntRepositoryJDBC.findUpcomingReturns(daysAhead);
    }

    public List<Emprunt> getEmpruntsByDocument(Document document) {
        return empruntRepositoryJDBC.findByDocument(document);
    }

    public boolean createEmprunt(Emprunteur emprunteur, Document document) {
        if (emprunteur.getAmendeBalance() > 0) {
            return false;
        }

        if (document.verifieDisponibilite()) {
            return false;
        }

        Emprunt emprunt = new Emprunt(nextEmpruntId++, emprunteur);
        EmpruntDetail empruntDetail = new EmpruntDetail(nextEmpruntDetailId++, document, emprunt);

        emprunt.addEmpruntDetail(empruntDetail);

        document.decrementExemplaires();

        emprunteur.getEmprunts().add(emprunt);

        return empruntRepositoryJDBC.save(emprunt);
    }

    public boolean retournerDocument(Emprunteur emprunteur, Document document) {
        Emprunt activeEmprunt = null;
        EmpruntDetail activeEmpruntDetail = null;

        for (Emprunt emprunt : emprunteur.getEmprunts()) {
            if (emprunt.getStatus().equals("Active")) {
                for (EmpruntDetail detail : emprunt.getEmpruntDetails()) {
                    if (detail.getDocument().getDocumentID() == document.getDocumentID()
                            && detail.getDateRetourActuelle() == null) {
                        activeEmprunt = emprunt;
                        activeEmpruntDetail = detail;
                        break;
                    }
                }
            }
            if (activeEmprunt != null)
                break;
        }

        if (activeEmpruntDetail == null) {
            return false;
        }

        activeEmpruntDetail.setDateRetourActuelle(new Date());
        activeEmpruntDetail.updateStatus();

        long daysOverdue = activeEmpruntDetail.isEnRetard();
        if (daysOverdue > 0) {
            double fineAmount = activeEmpruntDetail.calculAmende();
            AmendeService amendeService = new AmendeService();
            amendeService.createAmende(emprunteur, activeEmpruntDetail, fineAmount);
        }

        document.incrementExemplaires();

        boolean allReturned = true;
        for (EmpruntDetail detail : activeEmprunt.getEmpruntDetails()) {
            if (detail.getDateRetourActuelle() == null) {
                allReturned = false;
                break;
            }
        }

        if (allReturned) {
            activeEmprunt.setStatus("Completed");
        }

        return empruntRepositoryJDBC.update(activeEmprunt);
    }

    public List<Emprunt> getEmpruntsForEmprunteur(Emprunteur emprunteur) {
        return empruntRepositoryJDBC.findByEmprunteur(emprunteur);
    }

    public List<Emprunt> getAllEmprunts() {
        return empruntRepositoryJDBC.findAll();
    }
}