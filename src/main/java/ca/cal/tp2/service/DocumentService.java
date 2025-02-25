package ca.cal.tp2.service;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.dto.LivreDTO;
import ca.cal.tp2.repository.DocumentRepository;

import java.util.List;

public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService() {
        this.documentRepository = new DocumentRepository();
    }

    public LivreDTO createInitialBook() {
        return new LivreDTO(1, "Java Programming", 5, "123456789", "John Doe", "TechBooks", 500);
    }

    public boolean addDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document findById(int documentId) {
        return documentRepository.findById(documentId);
    }

    public List<Document> searchByTitle(String title) {
        return documentRepository.findByTitle(title);
    }

    public List<Document> searchByAuthor(String author) {
        return documentRepository.findByAuthor(author);
    }

    public List<Document> searchByPublisher(String publisher) {
        return documentRepository.findByPublisher(publisher);
    }

    public List<Document> searchByType(String type) {
        return documentRepository.findByType(type);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}