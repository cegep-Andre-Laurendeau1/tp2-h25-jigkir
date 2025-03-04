package ca.cal.tp2.service;

import ca.cal.tp2.model.Amende;
import ca.cal.tp2.model.EmpruntDetail;
import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.repository.AmendeRepositoryJDBC;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AmendeService {
    private final AmendeRepositoryJDBC amendeRepositoryJDBC = new AmendeRepositoryJDBC();
    private static int nextAmendeId = 1;

    public Amende createAmende(Emprunteur emprunteur, EmpruntDetail empruntDetail, double montant) {
        Amende amende = new Amende(nextAmendeId++, montant, emprunteur, empruntDetail);
        emprunteur.addAmende(montant);
        amendeRepositoryJDBC.save(amende);
        return amende;
    }

    public boolean collectAmende(Emprunteur emprunteur, double montant) {
        if (montant <= 0 || montant > emprunteur.getAmendeBalance()) {
            return false;
        }
        List<Amende> unpaidAmendes = amendeRepositoryJDBC.findUnpaidByEmprunteur(emprunteur);
        double remainingPayment = montant;
        for (Amende amende : unpaidAmendes) {
            if (remainingPayment <= 0) break;
            if (remainingPayment >= amende.getMontant()) {
                amende.updateStatus();
                remainingPayment -= amende.getMontant();
            } else {
                break;
            }
        }
        emprunteur.setAmendeBalance(emprunteur.getAmendeBalance() - montant);
        return true;
    }

    public List<Amende> getAllAmendesByEmprunteur(Emprunteur emprunteur) {
        return amendeRepositoryJDBC.findUnpaidByEmprunteur(emprunteur);
    }

    public List<Amende> getAmendesByDateRange(Date debut, Date fin) {
        List<Amende> allAmendes = amendeRepositoryJDBC.findAll();
        List<Amende> filteredAmendes = new ArrayList<>();
        for (Amende amende : allAmendes) {
            LocalDate creation = amende.getDateCreation();
            if (creation != null && !creation.isBefore(debut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) && !creation.isAfter(fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                filteredAmendes.add(amende);
            }
        }
        return filteredAmendes;
    }

    public List<Amende> getAllAmendes() {
        return amendeRepositoryJDBC.findAll();
    }
}