package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.EmpruntDetails;
import ca.cal.tp2.model.Emprunteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class EmpruntDetailsRepositoryJPA implements InterfaceRepository<EmpruntDetails> {
    private final EntityManagerFactory entityManagerFactory=
            Persistence.createEntityManagerFactory("orders.pu");
    @Override
    public void save(EmpruntDetails empruntDetails) {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            if(get(empruntDetails.getId()) != null){
                entityManager.merge(empruntDetails);
            }
            else {
                entityManager.persist(empruntDetails);
            }

            entityManager.getTransaction().commit();
        }
    }

    @Override
    public EmpruntDetails get(Long id) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            TypedQuery<EmpruntDetails> query = entityManager.createQuery(
                    "SELECT empruntDetails FROM EmpruntDetails empruntDetails " +
                            "WHERE empruntDetails.id = :id", EmpruntDetails.class);
            query.setParameter("id", id);
            query.getSingleResult();
            entityManager.getTransaction().commit();
            return query.getSingleResult();
        } catch (Exception e) {
            throw new DatabaseException("EmpruntDetails not found");
        }
    }

    public List<EmpruntDetails> get(Emprunt emprunt) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            TypedQuery<EmpruntDetails> query = entityManager.createQuery(
                    "SELECT empruntDetails FROM EmpruntDetails empruntDetails " +
                            "WHERE empruntDetails.emprunt = :emprunt", EmpruntDetails.class);
            query.setParameter("emprunt", emprunt);
            query.getResultList();
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException("EmpruntDetails not found");
        }
    }

    @Override
    public List<EmpruntDetails> get(String titreSubString, LocalDate annePublication) {
        return List.of();
    }

    @Override
    public List<EmpruntDetails> get(String titreSubString) {
        return List.of();
    }

    @Override
    public List<EmpruntDetails> get(LocalDate annePublication) {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<EmpruntDetails> get(Emprunteur emprunteur) {
        return List.of();
    }
}