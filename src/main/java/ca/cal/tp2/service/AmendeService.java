package ca.cal.tp2.service;

import ca.cal.tp2.model.Amende;
import ca.cal.tp2.model.EmpruntDetail;
import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.repository.AmendeRepository;

import java.util.List;

public class AmendeService {
    private AmendeRepository amendeRepository;
    private static int nextAmendeId = 1;

    public AmendeService() {
        this.amendeRepository = new AmendeRepository();
    }

    public Amende createAmende(Emprunteur emprunteur, EmpruntDetail empruntDetail, double montant) {
        Amende amende = new Amende(nextAmendeId++, montant, emprunteur, empruntDetail);
        emprunteur.addAmende(montant);
        amendeRepository.save(amende);
        return amende;
    }

    public boolean collectAmende(Emprunteur emprunteur, double montant) {
        if (montant <= 0 || montant > emprunteur.getAmendeBalance()) {
            return false;
        }
        List<Amende> unpaidAmendes = amendeRepository.findUnpaidByEmprunteur(emprunteur);
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

    public List<Amende> getAllAmendes() {
        return amendeRepository.findAll();
    }

    public List<Amende> getAmendesByEmprunteur(Emprunteur emprunteur) {
        return amendeRepository.findByEmprunteur(emprunteur);
    }
}