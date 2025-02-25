package ca.cal.tp2.repository;

import ca.cal.tp2.model.Document;

import java.util.List;

public interface IDocumentRepository extends IBaseRepository<Document, Integer> {
    List<Document> findByTitle(String title);
    List<Document> findByAuthor(String author);
    List<Document> findByPublisher(String publisher);
    List<Document> findByType(String type);
}
