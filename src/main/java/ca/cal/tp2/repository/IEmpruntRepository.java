package ca.cal.tp2.repository;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.EmpruntDetail;
import ca.cal.tp2.model.Emprunteur;
import java.util.List;

public interface IEmpruntRepository extends IBaseRepository<Emprunt, Integer> {
    List<Emprunt> findByEmprunteur(Emprunteur emprunteur);
    List<Emprunt> findByMonth(int month, int year);
    List<EmpruntDetail> findUpcomingReturns(int daysAhead);
    List<Emprunt> findByDocument(Document document);
}