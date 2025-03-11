package ca.cal.tp2.repository;

import ca.cal.tp2.exception.DatabaseException;
import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.Emprunteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class DocumentRepositoryJPA implements InterfaceRepository<Document> {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("orders.pu");

    @Override
    public void save(Document document) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            try {
                get(document.getId());
                entityManager.merge(document);
            } catch (Exception e) {
                entityManager.persist(document);
            }

            entityManager.getTransaction().commit();
        } catch (DatabaseException e) {
            throw new DatabaseException("Erreur lors de la sauvegarde du document");
        }
    }

    @Override
    public Document get(Long id) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            TypedQuery<Document> query = entityManager.createQuery(
                    "SELECT document FROM Document document " +
                            "WHERE document.id = :id",
                    Document.class);
            query.setParameter("id", id);
            query.getSingleResult();
            entityManager.getTransaction().commit();
            return query.getSingleResult();
        } catch (Exception e) {
            throw new DatabaseException("Document not found");
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Document> get(Emprunteur emprunteur) {
        return List.of();
    }

    @Override
    public List<Document> get(Emprunt emprunt) {
        return List.of();
    }

    public List<Document> get(String titreSubString, LocalDate annePublication) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            TypedQuery<Document> query = entityManager.createQuery(
                    "SELECT document FROM Document document " +
                            "WHERE document.titre LIKE :titreSubString " +
                            "AND document.anneePublication = :anneePublication ",
                    Document.class);
            query.setParameter("titreSubString", "%" + titreSubString + "%");
            query.setParameter("anneePublication", annePublication);
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException("Document not found");
        }
    }

    @Override
    public List<Document> get(String titreSubString) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            TypedQuery<Document> query = entityManager.createQuery(
                    "SELECT document FROM Document document " +
                            "WHERE document.titre LIKE :titreSubString ",
                    Document.class);
            query.setParameter("titreSubString", "%" + titreSubString + "%");
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException("Document not found");
        }
    }

    @Override
    public List<Document> get(LocalDate annePublication) throws DatabaseException {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            TypedQuery<Document> query = entityManager.createQuery(
                    "SELECT document FROM Document document " +
                            "WHERE document.anneePublication = :anneePublication ",
                    Document.class);
            query.setParameter("anneePublication", annePublication);
            entityManager.getTransaction().commit();
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException("Document not found");
        }
    }
}