package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.Emprunteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class EmprunteurRepositoryJPA implements InterfaceRepository<Emprunteur> {
    private final EntityManagerFactory entityManagerFactory=
            Persistence.createEntityManagerFactory("orders.pu");
    @Override
    public void save(Emprunteur emprunteur) throws DatabaseException {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            entityManager.persist(emprunteur);
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            throw new DatabaseException("Erreur lors de la sauvegarde de l'emprunteur");
        }
    }

    @Override
    public Emprunteur get(Long id) throws DatabaseException{
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            TypedQuery<Emprunteur> query = entityManager.createQuery(
                    "SELECT emprunteur FROM Emprunteur emprunteur " +
                            "WHERE emprunteur.id = :id", Emprunteur.class);
            query.setParameter("id", id);
            query.getSingleResult();
            entityManager.getTransaction().commit();
            return query.getSingleResult();
        } catch (Exception e) {
            throw new DatabaseException("Erreur lors de la récupération de l'emprunteur");
        }
    }

    @Override
    public List<Emprunteur> get(String titreSubString, LocalDate annePublication) {
        return List.of();
    }

    @Override
    public List<Emprunteur> get(String titreSubString) {
        return List.of();
    }

    @Override
    public List<Emprunteur> get(LocalDate annePublication) {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Emprunteur> get(Emprunteur emprunteur) {
        return List.of();
    }

    @Override
    public List<Emprunteur> get(Emprunt emprunt) {
        return List.of();
    }
}