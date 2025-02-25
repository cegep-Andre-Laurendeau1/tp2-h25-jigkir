package ca.cal.tp2.repository;

import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.model.Utilisateur;
import java.util.List;

public interface IUtilisateurRepository extends IBaseRepository<Utilisateur, Integer> {
    List<Emprunteur> findAllEmprunteurs();
}
