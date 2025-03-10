package ca.cal.tp2.service;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.service.dto.LivreDTO;
import ca.cal.tp2.repository.DocumentRepositoryJDBC;

import java.util.List;

public class DocumentService {
    private final DocumentRepositoryJDBC DocumentRepositoryJDBC;

    public DocumentService() {
        this.DocumentRepositoryJDBC = new DocumentRepositoryJDBC();
    }

    public LivreDTO createInitialBook() {
        return new LivreDTO(1L, "Java Programming", 5, "123456789", "John Doe", "TechBooks", 500);    }

    public boolean addDocument(Document document) {
        return DocumentRepositoryJDBC.save(document);
    }

    public Document findById(Long documentId) {
        return DocumentRepositoryJDBC.get(documentId);
    }

    public List<Document> searchByTitle(String title) {
        return DocumentRepositoryJDBC.findByTitle(title);
    }

    public List<Document> searchByAuthor(String author) {
        return DocumentRepositoryJDBC.findByAuthor(author);
    }

    public List<Document> searchByPublisher(String publisher) {
        return DocumentRepositoryJDBC.findByPublisher(publisher);
    }

    public List<Document> searchByType(String type) {
        return DocumentRepositoryJDBC.findByType(type);
    }

    public List<Document> getAllDocuments() {
        return DocumentRepositoryJDBC.findAll();
    }
}