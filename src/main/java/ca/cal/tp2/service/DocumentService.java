package ca.cal.tp2.service;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.service.dto.LivreDTO;
import ca.cal.tp2.repository.DocumentRepositoryJDBC;

import java.util.List;

public class DocumentService {
    private final DocumentRepositoryJDBC documentRepositoryJDBC;

    public DocumentService() {
        this.documentRepositoryJDBC = new DocumentRepositoryJDBC();
    }

    public LivreDTO createInitialBook() {
        return new LivreDTO(1L, "Java Programming", 5, "123456789", "John Doe", "TechBooks", 500);
    }

    public boolean addDocument(Document document) {
        return documentRepositoryJDBC.save(document);
    }

    public Document findById(Long documentId) {
        return documentRepositoryJDBC.get(documentId);
    }

    public List<Document> searchByTitle(String title) {
        return documentRepositoryJDBC.findByTitle(title);
    }

    public List<Document> searchByAuthor(String author) {
        return documentRepositoryJDBC.findByAuthor(author);
    }

    public List<Document> searchByPublisher(String publisher) {
        return documentRepositoryJDBC.findByPublisher(publisher);
    }

    public List<Document> searchByType(String type) {
        return documentRepositoryJDBC.findByType(type);
    }

    public List<Document> getAllDocuments() {
        return documentRepositoryJDBC.findAll();
    }
}