package ca.cal.tp2.repository;

import ca.cal.tp2.model.Amende;
import ca.cal.tp2.model.Emprunteur;
import java.util.List;

public interface IAmendeRepository extends IBaseRepository<Amende, Integer> {
    List<Amende> findUnpaidByEmprunteur(Emprunteur emprunteur);
}
