package ca.cal.tp2.repository;

import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.Emprunteur;

import java.time.LocalDate;
import java.util.List;

public interface InterfaceRepository <T> {
    public void save(T object);

    public T get(Long id);

    public List<T> get(String titreSubString, LocalDate annePublication);

    public List<T> get(String titreSubString);

    public List<T> get(LocalDate annePublication);

    public void delete(Long id);

    public List<T> get(Emprunteur emprunteur);
    public List<T> get(Emprunt emprunt);
}