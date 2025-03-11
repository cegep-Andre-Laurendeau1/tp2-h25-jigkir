package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.EmpruntDetails;
import ca.cal.tp2.model.Emprunteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class EmpruntRepositoryJPA implements InterfaceRepository<Emprunt> {
    private final EntityManagerFactory entityManagerFactory=
            Persistence.createEntityManagerFactory("orders.pu");
    @Override
    public void save(Emprunt emprunt) throws DatabaseException {
        try(EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            Emprunt empruntSansDetails = new Emprunt(emprunt.getDateEmprunt(), emprunt.getStatus(), emprunt.getEmprunteur());
            entityManager.persist(empruntSansDetails);

            for (int i = 0; i < emprunt.getEmpruntDetails().toArray().length; i++) {
                EmpruntDetails empruntDetailsCourant = emprunt.getEmpruntDetails().get(i);
                Document documentCourant = empruntDetailsCourant.getDocument();
                documentCourant.setNombreExemplaire(documentCourant.getNombreExemplaire()-1);

                entityManager.persist(new EmpruntDetails(empruntDetailsCourant.getDateRetourPrevue(), empruntDetailsCourant.getStatus(), empruntSansDetails,empruntDetailsCourant.getDocument()));
                entityManager.merge(documentCourant);
            }
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            throw new DatabaseException("Erreur lors de la sauvegarde de l'emprunt");
        }
    }

    @Override
    public Emprunt get(Long id) throws DatabaseException{
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            TypedQuery<Emprunt> query = entityManager.createQuery(
                    "SELECT emprunt FROM Emprunt emprunt " +
                            "WHERE emprunt.id = :id", Emprunt.class);
            query.setParameter("id", id);
            query.getSingleResult();
            entityManager.getTransaction().commit();
            return query.getSingleResult();
        } catch (Exception e) {
            throw new DatabaseException("Erreur lors de la récupération de l'emprunt");
        }
    }

    public List<Emprunt> get(Emprunteur emprunteur) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()){
            entityManager.getTransaction().begin();
            TypedQuery<Emprunt> query = entityManager.createQuery(
                    "SELECT emprunt FROM Emprunt emprunt " +
                            "WHERE emprunt.emprunteur.id = :idEmprunteur", Emprunt.class);
            query.setParameter("idEmprunteur", emprunteur.getId());
            query.getResultList();
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException("Erreur lors de la récupération de l'emprunt");
        }
    }

    @Override
    public List<Emprunt> get(Emprunt emprunt) {
        return List.of();
    }

    @Override
    public List<Emprunt> get(String titreSubString, LocalDate annePublication) {
        return List.of();
    }

    @Override
    public List<Emprunt> get(String titreSubString) {
        return List.of();
    }

    @Override
    public List<Emprunt> get(LocalDate annePublication) {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}